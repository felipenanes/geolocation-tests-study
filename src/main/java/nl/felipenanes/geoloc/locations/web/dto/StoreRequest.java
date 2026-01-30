package nl.felipenanes.geoloc.locations.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Store location search request")
public record StoreRequest(
    @Schema(description = "Latitude coordinate", example = "52.370216", minimum = "-90", maximum = "90")
    @NotNull(message = "{validation.latitude.required}")
    @DecimalMin(value = ValidationConstants.LATITUDE_MIN, message = "{validation.latitude.min}")
    @DecimalMax(value = ValidationConstants.LATITUDE_MAX, message = "{validation.latitude.max}")
    BigDecimal latitude,

    @Schema(description = "Longitude coordinate", example = "4.895168", minimum = "-180", maximum = "180")
    @NotNull(message = "{validation.longitude.required}")
    @DecimalMin(value = ValidationConstants.LONGITUDE_MIN, message = "{validation.longitude.min}")
    @DecimalMax(value = ValidationConstants.LONGITUDE_MAX, message = "{validation.longitude.max}")
    BigDecimal longitude
) {

    private static final class ValidationConstants {
        static final String LATITUDE_MIN = "-90.0";
        static final String LATITUDE_MAX = "90.0";
        static final String LONGITUDE_MIN = "-180.0";
        static final String LONGITUDE_MAX = "180.0";
    }
}
