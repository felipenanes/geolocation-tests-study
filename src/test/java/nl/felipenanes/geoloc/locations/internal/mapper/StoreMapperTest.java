package nl.felipenanes.geoloc.locations.internal.mapper;

import nl.felipenanes.geoloc.TestDataLoader;
import nl.felipenanes.geoloc.locations.internal.repository.StoreProjection;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import nl.felipenanes.geoloc.locations.internal.entity.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StoreMapper Unit Tests")
class StoreMapperTest {

    private StoreMapper storeMapper;
    private Store store;

    @BeforeEach
    void setUp() {
        storeMapper = new StoreMapper();
        store = TestDataLoader.storeAmsterdam().build();
    }

    @Test
    @DisplayName("Should convert StoreProjection to StoreResponse with correct data")
    void toResponse_shouldReturnCorrectStoreResponse() {
        // Given
        StoreProjection projection = TestDataLoader.storeProjectionFromStore(store, 2500.0);

        // When
        StoreResponse response = storeMapper.toResponse(projection);

        // Then
        assertThat(response.uuid()).isEqualTo(store.getUuid());
        assertThat(response.addressName()).isEqualTo(store.getAddressName());
        assertThat(response.city()).isEqualTo(store.getCity());
        assertThat(response.postalCode()).isEqualTo(store.getPostalCode());
        assertThat(response.street()).isEqualTo(store.getStreet());
        assertThat(response.street2()).isEqualTo(store.getStreet2());
        assertThat(response.street3()).isEqualTo(store.getStreet3());
        assertThat(response.latitude()).isEqualTo(store.getLatitude());
        assertThat(response.longitude()).isEqualTo(store.getLongitude());
        assertThat(response.locationType()).isEqualTo(store.getLocationType());
        assertThat(response.collectionPoint()).isEqualTo(store.getCollectionPoint());
        assertThat(response.todayOpen()).isEqualTo(store.getTodayOpen());
        assertThat(response.todayClose()).isEqualTo(store.getTodayClose());
        assertThat(response.distanceKm()).isEqualTo(2.5);
    }

    @Test
    @DisplayName("Should handle null street2 and street3 in response")
    void toResponse_shouldHandleNullStreetFields() {
        // Given
        StoreProjection projection = TestDataLoader.storeProjectionWithNullStreets(store, 1000.0);

        // When
        StoreResponse response = storeMapper.toResponse(projection);

        // Then
        assertThat(response.street2()).isNull();
        assertThat(response.street3()).isNull();
    }

    @Test
    @DisplayName("Should round distance to 2 decimal places")
    void toResponse_shouldRoundDistanceToTwoDecimalPlaces() {
        // Given
        StoreProjection projection = TestDataLoader.storeProjectionFromStore(store, 2567.89);

        // When
        StoreResponse response = storeMapper.toResponse(projection);

        // Then
        assertThat(response.distanceKm()).isEqualTo(2.57);
    }

    @Test
    @DisplayName("Should handle zero distance")
    void toResponse_shouldHandleZeroDistance() {
        // Given
        StoreProjection projection = TestDataLoader.storeProjectionFromStore(store, 0.0);

        // When
        StoreResponse response = storeMapper.toResponse(projection);

        // Then
        assertThat(response.distanceKm()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("Should convert list of projections to list of responses")
    void toResponse_shouldConvertList() {
        // Given
        StoreProjection projection1 = TestDataLoader.storeProjectionFromStore(store, 1000.0);
        StoreProjection projection2 = TestDataLoader.storeProjectionRotterdamCustom(2000.0);
        List<StoreProjection> projections = List.of(projection1, projection2);

        // When
        List<StoreResponse> responses = storeMapper.toResponse(projections);

        // Then
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).distanceKm()).isEqualTo(1.0);
        assertThat(responses.get(1).distanceKm()).isEqualTo(2.0);
        assertThat(responses.get(0).city()).isEqualTo(store.getCity());
        assertThat(responses.get(1).city()).isEqualTo("Rotterdam");
    }
}
