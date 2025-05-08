package cz.pycrs.cloudquery.controller;

import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    public Place getPlaceById(@PathVariable int id) {
        return placeService.getPlace(id);
    }

    @GetMapping("/list")
    @Operation(
            summary = "Získat všechna místa"
    )
    public Iterable<Place> getPlaces() {
        return placeService.getPlaces();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Smazat místo podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID místa")
            }
    )
    public ResponseEntity<?> deletePlace(@PathVariable int id) {
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}
