package nl.felipenanes.geoloc.locations.internal.service;

import nl.felipenanes.geoloc.locations.web.dto.StoreRequest;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;

import java.util.List;

public interface StoreService {
    List<StoreResponse> findNearestStores(StoreRequest request);
}
