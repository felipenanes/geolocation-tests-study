package nl.felipenanes.geoloc.locations;

import nl.felipenanes.geoloc.TestDataLoader;
import nl.felipenanes.geoloc.locations.internal.entity.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Store Entity Unit Tests")
class StoreEntityTest {

    private Store store;

    @BeforeEach
    void setUp() {
        store = TestDataLoader.storeAmsterdam().build();
    }

    @Test
    @DisplayName("Should work with builder pattern")
    void builder_shouldCreateValidStore() {
        // When
        Store builtStore = Store.builder()
                .uuid(UUID.randomUUID())
                .addressName("Builder Store")
                .city("Rotterdam")
                .postalCode("3011 AD")
                .street("Builder Street")
                .latitude(new BigDecimal("51.9225"))
                .longitude(new BigDecimal("4.4792"))
                .locationType("CONVENIENCE")
                .collectionPoint(false)
                .todayOpen("07:00")
                .todayClose("21:00")
                .showWarningMessage(true)
                .build();

        // Then
        assertThat(builtStore.getUuid()).isNotNull();
        assertThat(builtStore.getAddressName()).isEqualTo("Builder Store");
        assertThat(builtStore.getCity()).isEqualTo("Rotterdam");
        assertThat(builtStore.getPostalCode()).isEqualTo("3011 AD");
        assertThat(builtStore.getStreet()).isEqualTo("Builder Street");
        assertThat(builtStore.getLatitude()).isEqualTo(new BigDecimal("51.9225"));
        assertThat(builtStore.getLongitude()).isEqualTo(new BigDecimal("4.4792"));
        assertThat(builtStore.getLocationType()).isEqualTo("CONVENIENCE");
        assertThat(builtStore.getCollectionPoint()).isFalse();
        assertThat(builtStore.getTodayOpen()).isEqualTo("07:00");
        assertThat(builtStore.getTodayClose()).isEqualTo("21:00");
        assertThat(builtStore.getShowWarningMessage()).isTrue();
    }

    @Test
    @DisplayName("Should create store with all args constructor")
    void allArgsConstructor_shouldCreateValidStore() {
        // Given
        UUID uuid = UUID.randomUUID();

        // When
        Store store = new Store();
        store.setUuid(uuid);
        store.setAddressName("All Args Store");
        store.setCity("Utrecht");
        store.setPostalCode("3511 AX");
        store.setStreet("All Args Street");
        store.setStreet2("Suite 100");
        store.setStreet3("Floor 5");
        store.setLatitude(new BigDecimal("52.0894441"));
        store.setLongitude(new BigDecimal("5.1077468"));
        store.setSapStoreId("STORE002");
        store.setComplexNumber("COMPLEX456");
        store.setShowWarningMessage(false);
        store.setTodayOpen("09:00");
        store.setTodayClose("18:00");
        store.setLocationType("DISCOUNT");
        store.setCollectionPoint(false);

        // Then
        assertThat(store.getUuid()).isEqualTo(uuid);
        assertThat(store.getAddressName()).isEqualTo("All Args Store");
        assertThat(store.getCity()).isEqualTo("Utrecht");
        assertThat(store.getPostalCode()).isEqualTo("3511 AX");
        assertThat(store.getStreet()).isEqualTo("All Args Street");
        assertThat(store.getStreet2()).isEqualTo("Suite 100");
        assertThat(store.getStreet3()).isEqualTo("Floor 5");
        assertThat(store.getLatitude()).isEqualTo(new BigDecimal("52.0894441"));
        assertThat(store.getLongitude()).isEqualTo(new BigDecimal("5.1077468"));
        assertThat(store.getSapStoreId()).isEqualTo("STORE002");
        assertThat(store.getComplexNumber()).isEqualTo("COMPLEX456");
        assertThat(store.getShowWarningMessage()).isEqualTo(false);
        assertThat(store.getTodayOpen()).isEqualTo("09:00");
        assertThat(store.getTodayClose()).isEqualTo("18:00");
        assertThat(store.getLocationType()).isEqualTo("DISCOUNT");
        assertThat(store.getCollectionPoint()).isEqualTo(false);
    }

    @Test
    @DisplayName("Should create store with no args constructor")
    void noArgsConstructor_shouldCreateEmptyStore() {
        // When
        Store emptyStore = new Store();

        // Then
        assertThat(emptyStore).isNotNull();
        assertThat(emptyStore.getUuid()).isNull();
        assertThat(emptyStore.getAddressName()).isNull();
        assertThat(emptyStore.getCity()).isNull();
    }
}
