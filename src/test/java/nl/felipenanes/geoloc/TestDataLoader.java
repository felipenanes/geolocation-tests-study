package nl.felipenanes.geoloc;

import nl.felipenanes.geoloc.locations.web.dto.StoreRequest;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import nl.felipenanes.geoloc.locations.internal.entity.Store;
import nl.felipenanes.geoloc.locations.internal.repository.StoreProjection;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TestDataLoader {

    public static Store.StoreBuilder storeAmsterdam() {
        return Store.builder()
                .uuid(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                .addressName("Amsterdam Central")
                .city("Amsterdam")
                .postalCode("1012 AB")
                .street("Damrak")
                .latitude(new BigDecimal("52.3730796"))
                .longitude(new BigDecimal("4.8924534"))
                .locationType("SUPERMARKET")
                .collectionPoint(true)
                .todayOpen("08:00")
                .todayClose("22:00")
                .showWarningMessage(false);
    }

    public static List<StoreResponse> fiveStoreResponses() {
        return List.of(
                storeResponseAmsterdam(),
                storeResponseRotterdam(),
                storeResponseUtrecht(),
                storeResponseDenBosch(),
                storeResponseEindhoven()
        );
    }

    public static List<StoreProjection> fiveStoreProjections() {
        return List.of(
            storeProjectionAmsterdam(),
            storeProjectionRotterdam(),
            storeProjectionUtrecht(),
            storeProjectionDenBosch(),
            storeProjectionEindhoven()
        );
    }

    public static StoreResponse storeResponseAmsterdam() {
        return new StoreResponse(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                "Amsterdam Central",
                "Amsterdam",
                "1012 AB",
                "Damrak",
                null,
                null,
                new BigDecimal("52.3730796"),
                new BigDecimal("4.8924534"),
                "SUPERMARKET",
                true,
                "08:00",
                "22:00",
                0.5
        );
    }

    public static StoreResponse storeResponseRotterdam() {
        return new StoreResponse(
                UUID.fromString("660e8400-e29b-41d4-a716-446655440001"),
                "Rotterdam Central",
                "Rotterdam",
                "3011 AH",
                "Stationsplein",
                null,
                null,
                new BigDecimal("51.924420"),
                new BigDecimal("4.477732"),
                "SUPERMARKET",
                true,
                "07:00",
                "22:00",
                2.5
        );
    }

    public static StoreResponse storeResponseUtrecht() {
        return new StoreResponse(
                UUID.fromString("770e8400-e29b-41d4-a716-446655440002"),
                "Utrecht Central",
                "Utrecht",
                "3511 AX",
                "Stationsplein",
                null,
                null,
                new BigDecimal("52.089444"),
                new BigDecimal("5.107747"),
                "CONVENIENCE",
                false,
                "08:00",
                "21:00",
                3.5
        );
    }

    public static StoreResponse storeResponseDenBosch() {
        return new StoreResponse(
                UUID.fromString("880e8400-e29b-41d4-a716-446655440003"),
                "'s-Hertogenbosch Central Station",
                "'s-Hertogenbosch",
                "5211 XC",
                "Stationsplein 1",
                null,
                null,
                new BigDecimal("51.690583"),
                new BigDecimal("5.291372"),
                "CONVENIENCE",
                true,
                "06:00",
                "23:00",
                4.5
        );
    }

    public static StoreResponse storeResponseEindhoven() {
        return new StoreResponse(
                UUID.fromString("990e8400-e29b-41d4-a716-446655440004"),
                "Eindhoven Central Station",
                "Eindhoven",
                "5611 AB",
                "Stationsplein 15",
                null,
                null,
                new BigDecimal("51.441664"),
                new BigDecimal("5.476461"),
                "SUPERMARKET",
                true,
                "08:00",
                "22:00",
                5.5
        );
    }

    public static StoreProjection storeProjectionAmsterdam() {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); }
            @Override
            public String getCity() { return "Amsterdam"; }
            @Override
            public String getPostalCode() { return "1012 AB"; }
            @Override
            public String getStreet() { return "Damrak"; }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return "Amsterdam Central"; }
            @Override
            public BigDecimal getLongitude() { return new BigDecimal("4.8924534"); }
            @Override
            public BigDecimal getLatitude() { return new BigDecimal("52.3730796"); }
            @Override
            public String getLocationType() { return "SUPERMARKET"; }
            @Override
            public Boolean getCollectionPoint() { return true; }
            @Override
            public String getTodayOpen() { return "08:00"; }
            @Override
            public String getTodayClose() { return "22:00"; }
            @Override
            public Double getDistanceMeters() { return 500.0; }
        };
    }

    public static StoreProjection storeProjectionRotterdam() {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return UUID.fromString("660e8400-e29b-41d4-a716-446655440001"); }
            @Override
            public String getCity() { return "Rotterdam"; }
            @Override
            public String getPostalCode() { return "3011 AD"; }
            @Override
            public String getStreet() { return "Stationsplein"; }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return "Rotterdam Central"; }
            @Override
            public BigDecimal getLongitude() { return new BigDecimal("4.4777329"); }
            @Override
            public BigDecimal getLatitude() { return new BigDecimal("51.9244201"); }
            @Override
            public String getLocationType() { return "SUPERMARKET"; }
            @Override
            public Boolean getCollectionPoint() { return true; }
            @Override
            public String getTodayOpen() { return "08:00"; }
            @Override
            public String getTodayClose() { return "22:00"; }
            @Override
            public Double getDistanceMeters() { return 2500.0; }
        };
    }

    public static StoreProjection storeProjectionUtrecht() {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return UUID.fromString("770e8400-e29b-41d4-a716-446655440002"); }
            @Override
            public String getCity() { return "Utrecht"; }
            @Override
            public String getPostalCode() { return "3511 AX"; }
            @Override
            public String getStreet() { return "Stationsplein"; }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return "Utrecht Central"; }
            @Override
            public BigDecimal getLongitude() { return new BigDecimal("5.1077468"); }
            @Override
            public BigDecimal getLatitude() { return new BigDecimal("52.0894441"); }
            @Override
            public String getLocationType() { return "CONVENIENCE"; }
            @Override
            public Boolean getCollectionPoint() { return false; }
            @Override
            public String getTodayOpen() { return "07:00"; }
            @Override
            public String getTodayClose() { return "21:00"; }
            @Override
            public Double getDistanceMeters() { return 3500.0; }
        };
    }

    public static StoreProjection storeProjectionDenBosch() {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return UUID.fromString("770e8400-e29b-41d4-a716-446655433002"); }
            @Override
            public String getCity() { return "'s-Hertogenbosch"; }
            @Override
            public String getPostalCode() { return "5211 XC"; }
            @Override
            public String getStreet() { return "Stationsplein 1"; }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return "'s-Hertogenbosch Central Station"; }
            @Override
            public BigDecimal getLongitude() { return new BigDecimal("5.291372"); }
            @Override
            public BigDecimal getLatitude() { return new BigDecimal("51.690583"); }
            @Override
            public String getLocationType() { return "CONVENIENCE"; }
            @Override
            public Boolean getCollectionPoint() { return true; }
            @Override
            public String getTodayOpen() { return "06:00"; }
            @Override
            public String getTodayClose() { return "23:00"; }
            @Override
            public Double getDistanceMeters() { return 4500.0; }
        };
    }

    public static StoreProjection storeProjectionEindhoven() {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return UUID.fromString("770e8400-e29b-41d4-a716-446655442502"); }
            @Override
            public String getCity() { return "Eindhoven"; }
            @Override
            public String getPostalCode() { return "5611 AB"; }
            @Override
            public String getStreet() { return "Stationsplein 15"; }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return "Eindhoven Central Station"; }
            @Override
            public BigDecimal getLongitude() { return new BigDecimal("5.476461"); }
            @Override
            public BigDecimal getLatitude() { return new BigDecimal("51.441664"); }
            @Override
            public String getLocationType() { return "SUPERMARKET"; }
            @Override
            public Boolean getCollectionPoint() { return true; }
            @Override
            public String getTodayOpen() { return "08:00"; }
            @Override
            public String getTodayClose() { return "22:00"; }
            @Override
            public Double getDistanceMeters() { return 5500.0; }
        };
    }
    
    public static User.UserBuilder testUser() {
        return User.builder()
                .username("test@example.com")
                .password("encoded-password")
                .authorities(Collections.emptyList());
    }

    public static StoreProjection storeProjectionFromStore(Store store, Double distanceMeters) {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return store.getUuid(); }
            @Override
            public String getCity() { return store.getCity(); }
            @Override
            public String getPostalCode() { return store.getPostalCode(); }
            @Override
            public String getStreet() { return store.getStreet(); }
            @Override
            public String getStreet2() { return store.getStreet2(); }
            @Override
            public String getStreet3() { return store.getStreet3(); }
            @Override
            public String getAddressName() { return store.getAddressName(); }
            @Override
            public BigDecimal getLongitude() { return store.getLongitude(); }
            @Override
            public BigDecimal getLatitude() { return store.getLatitude(); }
            @Override
            public String getLocationType() { return store.getLocationType(); }
            @Override
            public Boolean getCollectionPoint() { return store.getCollectionPoint(); }
            @Override
            public String getTodayOpen() { return store.getTodayOpen(); }
            @Override
            public String getTodayClose() { return store.getTodayClose(); }
            @Override
            public Double getDistanceMeters() { return distanceMeters; }
        };
    }

    public static StoreProjection storeProjectionWithNullStreets(Store store, Double distanceMeters) {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return store.getUuid(); }
            @Override
            public String getCity() { return store.getCity(); }
            @Override
            public String getPostalCode() { return store.getPostalCode(); }
            @Override
            public String getStreet() { return store.getStreet(); }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return store.getAddressName(); }
            @Override
            public BigDecimal getLongitude() { return store.getLongitude(); }
            @Override
            public BigDecimal getLatitude() { return store.getLatitude(); }
            @Override
            public String getLocationType() { return store.getLocationType(); }
            @Override
            public Boolean getCollectionPoint() { return store.getCollectionPoint(); }
            @Override
            public String getTodayOpen() { return store.getTodayOpen(); }
            @Override
            public String getTodayClose() { return store.getTodayClose(); }
            @Override
            public Double getDistanceMeters() { return distanceMeters; }
        };
    }

    public static StoreProjection storeProjectionRotterdamCustom(Double distanceMeters) {
        return new StoreProjection() {
            @Override
            public UUID getUuid() { return UUID.fromString("660e8400-e29b-41d4-a716-446655440001"); }
            @Override
            public String getCity() { return "Rotterdam"; }
            @Override
            public String getPostalCode() { return "3011 AD"; }
            @Override
            public String getStreet() { return "Street 2"; }
            @Override
            public String getStreet2() { return null; }
            @Override
            public String getStreet3() { return null; }
            @Override
            public String getAddressName() { return "Store 2"; }
            @Override
            public BigDecimal getLongitude() { return new BigDecimal("4.4792"); }
            @Override
            public BigDecimal getLatitude() { return new BigDecimal("51.9225"); }
            @Override
            public String getLocationType() { return "CONVENIENCE"; }
            @Override
            public Boolean getCollectionPoint() { return false; }
            @Override
            public String getTodayOpen() { return "08:00"; }
            @Override
            public String getTodayClose() { return "20:00"; }
            @Override
            public Double getDistanceMeters() { return distanceMeters; }
        };
    }

    // StoreRequest factory methods
    public static StoreRequest amsterdamRequest() {
        return new StoreRequest(
                new BigDecimal("52.3730796"), 
                new BigDecimal("4.8924534"),
                5
        );
    }

    public static StoreRequest validRequest() {
        return amsterdamRequest();
    }

}
