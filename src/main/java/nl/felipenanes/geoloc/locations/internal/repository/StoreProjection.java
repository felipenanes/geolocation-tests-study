package nl.felipenanes.geoloc.locations.internal.repository;

import java.math.BigDecimal;
import java.util.UUID;

public interface StoreProjection {
    UUID getUuid();
    String getCity();
    String getPostalCode();
    String getStreet();
    String getStreet2();
    String getStreet3();
    String getAddressName();
    BigDecimal getLongitude();
    BigDecimal getLatitude();
    String getLocationType();
    Boolean getCollectionPoint();
    String getTodayOpen();
    String getTodayClose();
    Double getDistanceMeters();
}
