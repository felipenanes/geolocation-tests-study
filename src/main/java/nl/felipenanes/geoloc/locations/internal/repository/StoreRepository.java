package nl.felipenanes.geoloc.locations.internal.repository;

import nl.felipenanes.geoloc.locations.internal.entity.Store;
import nl.felipenanes.geoloc.locations.internal.exception.StoreNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {

    default List<StoreProjection> findNearestStoresOrThrow(double lat, double lng, int limit) {
        List<StoreProjection> nearestStores = findNearestStores(lat, lng, limit);

        if (nearestStores == null || nearestStores.isEmpty()) {
            throw new StoreNotFoundException("No stores available");
        }

        return nearestStores;
    }
    
    @Query(value = "SELECT uuid, city, postal_code as postalCode, street, street2, street3, " +
                   "address_name as addressName, longitude, latitude, location_type as locationType, " +
                   "collection_point as collectionPoint, today_open as todayOpen, today_close as todayClose, " +
                   "ST_Distance(location::geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography) " +
            "as distanceMeters " +
                   "FROM stores " +
                   "ORDER BY location <-> ST_SetSRID(ST_MakePoint(:lng, :lat), 4326) " +
                   "LIMIT :limit",
           nativeQuery = true)
    List<StoreProjection> findNearestStores(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("limit") int limit
    );
}
