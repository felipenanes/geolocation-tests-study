package nl.felipenanes.geoloc.locations.internal.mapper;

import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import nl.felipenanes.geoloc.locations.internal.repository.StoreProjection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreMapper {

    private static final double METERS_PER_KM = 1000.0;
    private static final int DECIMAL_PLACES = 2;

    public StoreResponse toResponse(StoreProjection projection) {
        double distanceKm = toKilometers(projection.getDistanceMeters());
        
        return new StoreResponse(
                projection.getUuid(),
                projection.getAddressName(),
                projection.getCity(),
                projection.getPostalCode(),
                projection.getStreet(),
                projection.getStreet2(),
                projection.getStreet3(),
                projection.getLatitude(),
                projection.getLongitude(),
                projection.getLocationType(),
                projection.getCollectionPoint(),
                projection.getTodayOpen(),
                projection.getTodayClose(),
                distanceKm
        );
    }

    public List<StoreResponse> toResponse(List<StoreProjection> projections) {
        return projections.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private double toKilometers(Double meters) {
        if (meters == null) return 0.0;
        double scale = Math.pow(10, DECIMAL_PLACES);
        return Math.round((meters / METERS_PER_KM) * scale) / scale;
    }
}
