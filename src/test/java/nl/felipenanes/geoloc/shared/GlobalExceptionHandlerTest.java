package nl.felipenanes.geoloc.shared;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import nl.felipenanes.geoloc.shared.internal.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("should handle validation errors correctly")
    void handleValidation_shouldReturnProblemDetail_withErrors() throws Exception {
        // Given
        TestRequest request = new TestRequest("", "invalid-email");
        
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "testRequest");
        bindingResult.addError(new FieldError("testRequest", "email", "must be a valid email"));
        bindingResult.addError(new FieldError("testRequest", "name", "must not be blank"));
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // When
        ProblemDetail result = globalExceptionHandler.handleValidation(exception);

        // Then
        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getTitle()).isEqualTo("Validation failed");
        assertThat(result.getProperties()).containsKey("errors");
        
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) result.getProperties().get("errors");
        assertThat(errors).hasSize(2);
        assertThat(errors.get("email")).isEqualTo("must be a valid email");
        assertThat(errors.get("name")).isEqualTo("must not be blank");
    }

    @Test
    @DisplayName("should handle validation errors with null message")
    void handleValidation_shouldUseDefaultMessage_whenFieldErrorHasNullMessage() throws Exception {
        // Given
        TestRequest request = new TestRequest("test", "test@test.com");
        
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "testRequest");
        FieldError fieldError = new FieldError("testRequest", "email", null, false, null, null, null);
        bindingResult.addError(fieldError);
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // When
        ProblemDetail result = globalExceptionHandler.handleValidation(exception);

        // Then
        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getTitle()).isEqualTo("Validation failed");
        
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) result.getProperties().get("errors");
        assertThat(errors).hasSize(1);
        assertThat(errors.get("email")).isEqualTo("invalid");
    }

    @Test
    @DisplayName("should handle empty validation errors")
    void handleValidation_shouldReturnEmptyErrors_whenNoFieldErrors() throws Exception {
        // Given
        TestRequest request = new TestRequest("test", "test@test.com");
        
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "testRequest");
        // No field errors added
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // When
        ProblemDetail result = globalExceptionHandler.handleValidation(exception);

        // Then
        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getTitle()).isEqualTo("Validation failed");
        
        @SuppressWarnings("unchecked")
        Map<String, String> errors = (Map<String, String>) result.getProperties().get("errors");
        assertThat(errors).isEmpty();
    }

    // Test record for validation
    public record TestRequest(
        @NotBlank(message = "must not be blank")
        String name,
        
        @Email(message = "must be a valid email")
        String email
    ) {}
}
