package nl.felipenanes.geoloc.locations.internal.service.impl;

import lombok.RequiredArgsConstructor;
import nl.felipenanes.geoloc.locations.internal.exception.StoreNotFoundException;
import nl.felipenanes.geoloc.locations.internal.mapper.StoreMapper;
import nl.felipenanes.geoloc.locations.internal.repository.StoreProjection;
import nl.felipenanes.geoloc.locations.internal.repository.StoreRepository;
import nl.felipenanes.geoloc.locations.internal.service.StoreService;
import nl.felipenanes.geoloc.locations.web.dto.StoreRequest;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    public List<StoreResponse> findNearestStores(StoreRequest request) {
        double userLat = request.latitude().doubleValue();
        double userLon = request.longitude().doubleValue();

        List<StoreProjection> nearestStores = storeRepository.findNearestStores(userLat, userLon);

        if (nearestStores == null || nearestStores.isEmpty()) {
            throw new StoreNotFoundException("No stores available");
        }

        return storeMapper.toResponse(nearestStores);
    }
}
