package nl.felipenanes.geoloc.auth.internal.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.felipenanes.geoloc.shared.internal.exception.ProblemDetailHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "nl.felipenanes.geoloc.auth")
@RequiredArgsConstructor
class AuthExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BadCredentialsException.class)
    ProblemDetail handleBadCredentials() {
        log.error("Authentication failed: invalid credentials");
        return ProblemDetailHelper.create(
            HttpStatus.UNAUTHORIZED,
            msg("auth.error.authentication_failed.title"),
            msg("auth.error.invalid_credentials")
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    ProblemDetail handleAuthentication(AuthenticationException ex) {
        log.error("Authentication failed: {}", ex.getMessage(), ex);
        return ProblemDetailHelper.create(
            HttpStatus.UNAUTHORIZED,
            msg("auth.error.authentication_failed.title"),
            msg("auth.error.unable_to_authenticate")
        );
    }

    private String msg(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
