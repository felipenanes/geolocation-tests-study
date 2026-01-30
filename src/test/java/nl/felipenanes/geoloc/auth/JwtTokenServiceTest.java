package nl.felipenanes.geoloc.auth;

import io.jsonwebtoken.MalformedJwtException;
import nl.felipenanes.geoloc.auth.internal.service.impl.JwtServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
@DisplayName("JwtTokenService")
class JwtTokenServiceTest {

    private JwtServiceImpl jwtServiceImpl;
    private UserDetails testUser;

    @BeforeEach
    void setUp() {
        jwtServiceImpl = new JwtServiceImpl("testSecretKeyThatIsLongEnoughForHS256Algorithm", 86400000L);
        
        testUser = User.builder()
            .username("test@example.com")
            .password("password")
            .authorities(Collections.emptyList())
            .build();
    }

    @Nested
    @DisplayName("generateToken")
    class GenerateToken {

        @Test
        @DisplayName("should generate a valid JWT token")
        void shouldGenerateValidToken() {
            String token = jwtServiceImpl.generateToken(testUser);

            assertThat(token).isNotNull();
            assertThat(token).isNotEmpty();
            assertThat(token.split("\\.")).hasSize(3); // header.payload.signature
        }

        @Test
        @DisplayName("should generate different tokens for same user")
        void shouldGenerateDifferentTokens() throws InterruptedException {
            String token1 = jwtServiceImpl.generateToken(testUser);
            Thread.sleep(1100); // JWT issuedAt has second precision
            String token2 = jwtServiceImpl.generateToken(testUser);

            assertThat(token1).isNotEqualTo(token2);
        }
    }

    @Nested
    @DisplayName("extractUsername")
    class ExtractUsername {

        @Test
        @DisplayName("should extract username from valid token")
        void shouldExtractUsername() {
            String token = jwtServiceImpl.generateToken(testUser);

            String username = jwtServiceImpl.extractUsername(token);

            assertThat(username).isEqualTo("test@example.com");
        }

        @Test
        @DisplayName("should throw exception for malformed token")
        void shouldThrowForMalformedToken() {
            assertThatThrownBy(() -> jwtServiceImpl.extractUsername("invalid.token"))
                .isInstanceOf(MalformedJwtException.class);
        }
    }

    @Nested
    @DisplayName("isTokenValid")
    class IsTokenValid {

        @Test
        @DisplayName("should return true for valid token and matching user")
        void shouldReturnTrueForValidToken() {
            String token = jwtServiceImpl.generateToken(testUser);

            boolean isValid = jwtServiceImpl.isTokenValid(token, testUser);

            assertThat(isValid).isTrue();
        }

        @Test
        @DisplayName("should return false for valid token but different user")
        void shouldReturnFalseForDifferentUser() {
            String token = jwtServiceImpl.generateToken(testUser);
            UserDetails differentUser = User.builder()
                .username("other@example.com")
                .password("password")
                .authorities(Collections.emptyList())
                .build();

            boolean isValid = jwtServiceImpl.isTokenValid(token, differentUser);

            assertThat(isValid).isFalse();
        }
    }

}
