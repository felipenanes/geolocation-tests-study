package nl.felipenanes.geoloc.locations.internal.repository;

import nl.felipenanes.geoloc.locations.internal.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, String> {
    
    @Query(value = "SELECT uuid, city, postal_code as postalCode, street, street2, street3, " +
                   "address_name as addressName, longitude, latitude, location_type as locationType, " +
                   "collection_point as collectionPoint, today_open as todayOpen, today_close as todayClose, " +
                   "ST_Distance(location::geography, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326)::geography) " +
            "as distanceMeters " +
                   "FROM stores " +
                   "ORDER BY location <-> ST_SetSRID(ST_MakePoint(:lng, :lat), 4326) " +
                   "LIMIT 5",
           nativeQuery = true)
    List<StoreProjection> findNearestStores(
            @Param("lat") double lat,
            @Param("lng") double lng
    );
}
