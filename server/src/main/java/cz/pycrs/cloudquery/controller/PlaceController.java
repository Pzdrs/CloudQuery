package cz.pycrs.cloudquery.controller;

import cz.pycrs.cloudquery.dto.PlacePatchRequest;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Získat místo podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID místa")
            }
    )
    public Place getPlaceById(
            @PathVariable int id
    ) {
        return placeService.getPlace(id);
    }

    @GetMapping("/list")
    @Operation(
            summary = "Získat všechna místa"
    )
    public Page<Place> getPlaces(
            Pageable pageable
    ) {
        return placeService.getPlaces(pageable);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Smazat místo podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID místa")
            }
    )
    public ResponseEntity<?> deletePlace(
            @PathVariable int id
    ) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Aktualizovat místo podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID místa")
            }
    )
    public Place updatePlace(
            @PathVariable int id, @RequestBody PlacePatchRequest patch
    ) {
        return placeService.updatePlace(id, patch);
    }
}
