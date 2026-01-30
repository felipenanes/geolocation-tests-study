package nl.felipenanes.geoloc.locations.web.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record StoreResponse(
    UUID uuid,
    String addressName,
    String city,
    String postalCode,
    String street,
    String street2,
    String street3,
    BigDecimal latitude,
    BigDecimal longitude,
    String locationType,
    Boolean collectionPoint,
    String todayOpen,
    String todayClose,
    Double distanceKm
) {}
