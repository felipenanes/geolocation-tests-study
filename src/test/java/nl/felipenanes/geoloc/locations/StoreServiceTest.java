package nl.felipenanes.geoloc.locations;

import nl.felipenanes.geoloc.TestDataLoader;
import nl.felipenanes.geoloc.locations.internal.exception.StoreNotFoundException;
import nl.felipenanes.geoloc.locations.internal.mapper.StoreMapper;
import nl.felipenanes.geoloc.locations.internal.repository.StoreProjection;
import nl.felipenanes.geoloc.locations.internal.repository.StoreRepository;
import nl.felipenanes.geoloc.locations.internal.service.impl.StoreServiceImpl;
import nl.felipenanes.geoloc.locations.web.dto.StoreRequest;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("StoreService Unit Tests")
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StoreMapper storeMapper;

    private StoreServiceImpl storeService;

    private StoreRequest validRequest;

    @BeforeEach
    void setUp() {
        storeService = new StoreServiceImpl(storeRepository, storeMapper);
        validRequest = new StoreRequest(
                new BigDecimal("52.370216"), 
                new BigDecimal("4.895168")
        );
    }

    @Test
    @DisplayName("Should find nearest stores when valid coordinates provided")
    void findNearestStores_shouldReturnStores_whenValidCoordinates() {
        // Given
        List<StoreProjection> projections = TestDataLoader.fiveStoreProjections();
        List<StoreResponse> expectedResponses = TestDataLoader.fiveStoreResponses();
        
        when(storeRepository.findNearestStores(any(Double.class), any(Double.class)))
                .thenReturn(projections);
        when(storeMapper.toResponse(projections))
                .thenReturn(expectedResponses);

        // When
        List<StoreResponse> result = storeService.findNearestStores(validRequest);

        // Then
        assertThat(result).hasSize(5);
        assertThat(result.get(0).uuid()).isNotNull();
        assertThat(result.get(0).addressName()).isNotNull();
        assertThat(result.get(0).city()).isNotNull();
        assertThat(result.get(0).distanceKm()).isNotNull();
        
        verify(storeRepository).findNearestStores(52.370216, 4.895168);
        verify(storeMapper).toResponse(projections);
    }

    @Test
    @DisplayName("Should throw StoreNotFoundException when no stores available")
    void findNearestStores_shouldThrowException_whenNoStoresAvailable() {
        // Given
        when(storeRepository.findNearestStores(any(Double.class), any(Double.class)))
                .thenReturn(List.of());

        // When & Then
        assertThatThrownBy(() -> storeService.findNearestStores(validRequest))
                .isInstanceOf(StoreNotFoundException.class)
                .hasMessage("No stores available");
        
        verify(storeRepository).findNearestStores(52.370216, 4.895168);
    }
}
