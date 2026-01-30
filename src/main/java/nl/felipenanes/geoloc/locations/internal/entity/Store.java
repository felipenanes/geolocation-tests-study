package nl.felipenanes.geoloc.locations.internal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.felipenanes.geoloc.locations.internal.repository.StoreProjection;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "stores")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.TIME) // UUID v7 based on time
    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "postal_code", nullable = false, length = 20)
    private String postalCode;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "street2")
    private String street2;

    @Column(name = "street3")
    private String street3;

    @Column(name = "address_name", nullable = false)
    private String addressName;

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal longitude;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private BigDecimal latitude;

    @Column(name = "complex_number", length = 20)
    private String complexNumber;

    @Column(name = "sap_store_id", length = 20)
    private String sapStoreId;

    @Column(name = "show_warning_message", nullable = false)
    private Boolean showWarningMessage;

    @Column(name = "today_open", length = 20)
    private String todayOpen;

    @Column(name = "today_close", length = 20)
    private String todayClose;

    @Column(name = "location_type", length = 50)
    private String locationType;

    @Column(name = "collection_point", nullable = false)
    private Boolean collectionPoint;

    @Column(name = "location", columnDefinition = "geography(Point,4326)")
    private String location;

}
