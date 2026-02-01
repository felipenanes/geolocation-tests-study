package nl.felipenanes.geoloc.locations.internal.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.felipenanes.geoloc.shared.internal.exception.ProblemDetailHelper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "nl.felipenanes.geoloc.locations")
@RequiredArgsConstructor
public class LocationsExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(StoreNotFoundException.class)
    ProblemDetail handleStoreNotFound(StoreNotFoundException ex) {
        log.error("Store not found: {}", ex.getMessage());
        return ProblemDetailHelper.create(
            HttpStatus.NOT_FOUND,
            msg("locations.error.store_not_found.title"),
            msg("locations.error.no_stores_available")
        );
    }

    private String msg(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }
}
