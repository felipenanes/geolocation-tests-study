package nl.felipenanes.geoloc.auth.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Authentication request")
public record AuthenticationRequest(
    @Schema(description = "User email address", example = "test@test.com")
    @NotBlank(message = "{validation.email.required}")
    @Email(message = "{validation.email.invalid}")
    String email,
    
    @Schema(description = "User password", example = "test123")
    @NotBlank(message = "{validation.password.required}")
    String password
) {}
