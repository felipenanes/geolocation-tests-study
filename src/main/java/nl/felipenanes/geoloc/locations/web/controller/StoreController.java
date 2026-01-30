package nl.felipenanes.geoloc.locations.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.felipenanes.geoloc.locations.web.dto.StoreRequest;
import nl.felipenanes.geoloc.locations.web.dto.StoreResponse;
import nl.felipenanes.geoloc.locations.internal.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
@Tag(name = "Locations", description = "Store location and proximity endpoints")
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/nearest")
    @Operation(summary = "Find nearest stores", description = "Find stores nearest to given geographic coordinates", 
               security = @SecurityRequirement(name = "bearer-jwt"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stores found successfully",
                content = @Content(schema = @Schema(implementation = StoreResponse.class))),
        @ApiResponse(responseCode = "404", description = "No stores found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "400", description = "Invalid coordinates")
    })
    public ResponseEntity<List<StoreResponse>> findNearestStore(
            @Valid @ModelAttribute StoreRequest request) {
        List<StoreResponse> response = storeService.findNearestStores(request);
        return ResponseEntity.ok(response);
    }

}
