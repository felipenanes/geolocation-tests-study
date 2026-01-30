package nl.felipenanes.geoloc.auth;

import nl.felipenanes.geoloc.auth.web.dto.AuthenticationRequest;
import nl.felipenanes.geoloc.auth.web.dto.AuthenticationResponse;
import nl.felipenanes.geoloc.user.internal.entity.Role;
import nl.felipenanes.geoloc.user.internal.entity.User;
import nl.felipenanes.geoloc.user.internal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("AuthController Integration Tests")
class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = RestClient.builder()
            .baseUrl("http://localhost:" + port)
            .defaultStatusHandler(status -> true, (request, response) -> {})
            .build();

        // Create test user if not exists
        if (userRepository.findByEmail("test@test.com").isEmpty()) {
            User testUser = User.builder()
                    .uuid(UUID.randomUUID())
                    .name("Test User")
                    .email("test@test.com")
                    .password(passwordEncoder.encode("test123"))
                    .role(Role.USER)
                    .build();
            userRepository.save(testUser);
        }
    }

    @Nested
    @DisplayName("POST /api/v1/auth/login")
    class Login {

        @Test
        @DisplayName("should return 200 and token for valid credentials")
        void shouldReturnTokenForValidCredentials() {
            AuthenticationResponse response = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest("test@test.com", "test123"))
                .retrieve()
                .body(AuthenticationResponse.class);

            assertThat(response).isNotNull();
            assertThat(response.token()).isNotNull();
        }

        @Test
        @DisplayName("should return 401 for invalid password")
        void shouldReturn401ForInvalidPassword() {
            HttpStatusCode status = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest("test@test.com", "wrong-password"))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

            assertThat(status.value()).isEqualTo(401);
        }

        @Test
        @DisplayName("should return 401 for non-existent user")
        void shouldReturn401ForNonExistentUser() {
            HttpStatusCode status = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest("nonexistent@test.com", "password"))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

            assertThat(status.value()).isEqualTo(401);
        }

        @Test
        @DisplayName("should return 400 for missing email")
        void shouldReturn400ForMissingEmail() {
            HttpStatusCode status = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest(null, "password"))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

            assertThat(status.value()).isEqualTo(400);
        }

        @Test
        @DisplayName("should return 400 for invalid email format")
        void shouldReturn400ForInvalidEmailFormat() {
            HttpStatusCode status = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest("invalid-email", "password"))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

            assertThat(status.value()).isEqualTo(400);
        }

        @Test
        @DisplayName("should return 400 for missing password")
        void shouldReturn400ForMissingPassword() {
            HttpStatusCode status = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest("test@test.com", null))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

            assertThat(status.value()).isEqualTo(400);
        }

        @Test
        @DisplayName("should return 400 for empty password")
        void shouldReturn400ForEmptyPassword() {
            HttpStatusCode status = restClient.post()
                .uri("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new AuthenticationRequest("test@test.com", ""))
                .retrieve()
                .toBodilessEntity()
                .getStatusCode();

            assertThat(status.value()).isEqualTo(400);
        }
    }
}
