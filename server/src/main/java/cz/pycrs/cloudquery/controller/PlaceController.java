package cz.pycrs.cloudquery.controller;

import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
    private final WeatherService weatherService;

    @GetMapping("/list")
    public Iterable<Place> getPlaces() {
        return weatherService.getPlaces();
    }
}
