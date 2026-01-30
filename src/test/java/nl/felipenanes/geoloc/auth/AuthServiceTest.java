package nl.felipenanes.geoloc.auth;

import nl.felipenanes.geoloc.TestDataLoader;
import nl.felipenanes.geoloc.auth.web.dto.AuthenticationRequest;
import nl.felipenanes.geoloc.auth.web.dto.AuthenticationResponse;
import nl.felipenanes.geoloc.auth.internal.service.AuthService;
import nl.felipenanes.geoloc.auth.internal.service.impl.AuthServiceImpl;
import nl.felipenanes.geoloc.auth.internal.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService")
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtServiceImpl jwtTokenService;

    private AuthService authService;

    private UserDetails testUser;

    @BeforeEach
    void setUp() {
        testUser = TestDataLoader.testUser().build();

        authService = new AuthServiceImpl(authenticationManager, jwtTokenService);
    }

    @Nested
    @DisplayName("authenticate")
    class Authenticate {

        @Test
        @DisplayName("should return token for valid credentials")
        void shouldReturnTokenForValidCredentials() {
            AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password");
            Authentication auth = mock(Authentication.class);
            
            when(auth.getPrincipal()).thenReturn(testUser);
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
            when(jwtTokenService.generateToken(testUser)).thenReturn("jwt-token");

            AuthenticationResponse response = authService.authenticate(request);

            assertThat(response).isNotNull();
            assertThat(response.token()).isEqualTo("jwt-token");
            verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
            verify(jwtTokenService).generateToken(testUser);
        }

        @Test
        @DisplayName("should throw BadCredentialsException for invalid credentials")
        void shouldThrowForInvalidCredentials() {
            AuthenticationRequest request = new AuthenticationRequest("test@example.com", "wrong-password");
            
            when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

            assertThatThrownBy(() -> authService.authenticate(request))
                .isInstanceOf(BadCredentialsException.class);
        }
    }

}
