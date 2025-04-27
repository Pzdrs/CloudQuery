package cz.pycrs.cloudquery.controller;

import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
    private final WeatherService weatherService;

    @GetMapping("/list")
    public Iterable<Place> getPlaces() {
        return weatherService.getPlaces();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePlace(@PathVariable int id) {
        weatherService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}
