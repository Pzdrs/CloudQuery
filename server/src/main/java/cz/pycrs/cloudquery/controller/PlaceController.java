package cz.pycrs.cloudquery.controller;

import cz.pycrs.cloudquery.dto.PlaceTemperatureDifference;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.service.PlaceService;
import cz.pycrs.cloudquery.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;

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

    @GetMapping("/rain-intensity")
    @Operation(
            summary = "Všechna místa kde v daný den nebo rozmezí dnů pršelo s danou intenzitou",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "intensity", description = "Intenzita deště"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "from", description = "Od data"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "to", description = "Do data")
            }
    )
    public Iterable<Place> placesWithCommonRainIntensity(
            @RequestParam double intensity,
            @RequestParam(required = false) ZonedDateTime from,
            @RequestParam(required = false) ZonedDateTime to
    ) {
        if (from == null) throw new IllegalArgumentException("From date is required");
        return placeService.commonRainIntensity(intensity, from, to);
    }

    @GetMapping("/temp-diff")
    @Operation(
            summary = "Najít místa, kde v daný den byl největší rozdíl teplot",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "targetDate", description = "Datum pro hledání rozdílu teplot"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "n", description = "Počet míst, která se mají vrátit")
            }
    )
    public Iterable<PlaceTemperatureDifference> placesWithMaxTempDiff(
            @RequestParam ZonedDateTime targetDate,
            @RequestParam(required = false) Integer n
    ) {
        if (targetDate == null) throw new IllegalArgumentException("TargetDate date is required");
        return placeService.largestTemperatureDifference(targetDate, n);
    }
}
