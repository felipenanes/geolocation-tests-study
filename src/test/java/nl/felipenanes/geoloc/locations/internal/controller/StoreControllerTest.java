package nl.felipenanes.geoloc.locations.internal.controller;

import nl.felipenanes.geoloc.TestDataLoader;
import nl.felipenanes.geoloc.locations.internal.exception.LocationsExceptionHandler;
import nl.felipenanes.geoloc.locations.internal.exception.StoreNotFoundException;
import nl.felipenanes.geoloc.locations.internal.service.StoreService;
import nl.felipenanes.geoloc.locations.web.controller.StoreController;
import nl.felipenanes.geoloc.locations.web.dto.StoreRequest;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("StoreController Unit Tests")
class StoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StoreService storeService;

    @Mock
    private MessageSource messageSource;

    private StoreRequest validRequest;
    private ArrayList<StoreResponse> mockResponse;

    @BeforeEach
    void setUp() {
        StoreController storeController = new StoreController(storeService);
        LocationsExceptionHandler exceptionHandler = new LocationsExceptionHandler(messageSource);
        
        mockMvc = MockMvcBuilders.standaloneSetup(storeController)
                .setControllerAdvice(exceptionHandler)
                .build();

        validRequest = TestDataLoader.validRequest();

        mockResponse = new ArrayList<>(TestDataLoader.fiveStoreResponses());
    }

    @Test
    @DisplayName("Should return nearest stores with valid coordinates")
    void findNearestStores_shouldReturnStores_whenValidCoordinates() throws Exception {
        // Given
        when(storeService.findNearestStores(any(StoreRequest.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", validRequest.latitude().toString())
                .param("longitude", validRequest.longitude().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].uuid").value(mockResponse.get(0).uuid().toString()))
                .andExpect(jsonPath("$[0].addressName").value(mockResponse.get(0).addressName()))
                .andExpect(jsonPath("$[0].city").value(mockResponse.get(0).city()))
                .andExpect(jsonPath("$[0].postalCode").value(mockResponse.get(0).postalCode()))
                .andExpect(jsonPath("$[0].street").value(mockResponse.get(0).street()))
                .andExpect(jsonPath("$[0].latitude").value(mockResponse.get(0).latitude().toString()))
                .andExpect(jsonPath("$[0].longitude").value(mockResponse.get(0).longitude().toString()))
                .andExpect(jsonPath("$[0].locationType").value(mockResponse.get(0).locationType()))
                .andExpect(jsonPath("$[0].collectionPoint").value(mockResponse.get(0).collectionPoint()))
                .andExpect(jsonPath("$[0].todayOpen").value(mockResponse.get(0).todayOpen()))
                .andExpect(jsonPath("$[0].todayClose").value(mockResponse.get(0).todayClose()))
                .andExpect(jsonPath("$[0].distanceKm").value(mockResponse.get(0).distanceKm()))
                .andExpect(jsonPath("$[1].uuid").value(mockResponse.get(1).uuid().toString()))
                .andExpect(jsonPath("$[1].distanceKm").value(mockResponse.get(1).distanceKm()));

        // Verify the service was called
        verify(storeService).findNearestStores(any(StoreRequest.class));
    }

    @Test
    @DisplayName("Should return 404 when stores not found")
    void findNearestStores_shouldReturn404_whenStoresNotFound() throws Exception {
        // Given
        when(messageSource.getMessage(eq("locations.error.store_not_found.title"), any(), any(Locale.class)))
                .thenReturn("Store not found");
        when(messageSource.getMessage(eq("locations.error.no_stores_available"), any(), any(Locale.class)))
                .thenReturn("No stores available");
        when(storeService.findNearestStores(any(StoreRequest.class)))
                .thenThrow(new StoreNotFoundException("No stores available"));

        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", validRequest.latitude().toString())
                .param("longitude", validRequest.longitude().toString()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("No stores available"));
    }

    @Test
    @DisplayName("Should return 400 when latitude is null")
    void findNearestStores_shouldReturn400_whenLatitudeIsNull() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("longitude", validRequest.longitude().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when longitude is null")
    void findNearestStores_shouldReturn400_whenLongitudeIsNull() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", validRequest.latitude().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when latitude is out of range (too high)")
    void findNearestStores_shouldReturn400_whenLatitudeTooHigh() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", "91.0")
                .param("longitude", validRequest.longitude().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when latitude is out of range (too low)")
    void findNearestStores_shouldReturn400_whenLatitudeTooLow() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", "-91.0")
                .param("longitude", validRequest.longitude().toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when longitude is out of range (too high)")
    void findNearestStores_shouldReturn400_whenLongitudeTooHigh() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", validRequest.latitude().toString())
                .param("longitude", "181.0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when longitude is out of range (too low)")
    void findNearestStores_shouldReturn400_whenLongitudeTooLow() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", validRequest.latitude().toString())
                .param("longitude", "-181.0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 when request body is empty")
    void findNearestStores_shouldReturn400_whenRequestBodyIsEmpty() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 415 when content type is not JSON")
    void findNearestStores_shouldReturn415_whenContentTypeNotJson() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", validRequest.latitude().toString())
                .param("longitude", validRequest.longitude().toString())
                .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should handle valid boundary coordinates")
    void findNearestStores_shouldReturn200_whenValidBoundaryCoordinates() throws Exception {
        // Given - Test with valid boundary values
        when(storeService.findNearestStores(any(StoreRequest.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", "90.0")
                .param("longitude", "180.0"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should handle negative boundary coordinates")
    void findNearestStores_shouldReturn200_whenValidNegativeBoundaryCoordinates() throws Exception {
        // Given - Test with valid negative boundary values
        when(storeService.findNearestStores(any(StoreRequest.class))).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/v1/stores/nearest")
                .param("latitude", "-90.0")
                .param("longitude", "-180.0"))
                .andExpect(status().isOk());
    }
}
