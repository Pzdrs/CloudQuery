package cz.pycrs.cloudquery.controller;

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
    private final WeatherService weatherService;
    private final PlaceService placeService;

    @GetMapping("/list")
    public Iterable<Place> getPlaces() {
        return weatherService.getPlaces();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable int id) {
        weatherService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rain-intensity")
    @Operation(
            summary = "Všechna místa kde v daný den nebo rozmezí dnů pršelo s danou intenzitou"
    )
    public Iterable<Place> placesWithCommonRainIntensity(
            @RequestParam double intensity,
            @RequestParam(required = false) ZonedDateTime from,
            @RequestParam(required = false) ZonedDateTime to
    ) {
        if (from == null) throw new IllegalArgumentException("From date is required");
        return placeService.commonRainIntensity(intensity, from, to);
    }
}
