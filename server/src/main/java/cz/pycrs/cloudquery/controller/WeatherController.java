package cz.pycrs.cloudquery.controller;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherMapClient owmClient;
    private final WeatherService weatherService;

    @GetMapping("/current")
    public Measurement currentWeather(
            @RequestParam(required = false) String city,
            @RequestParam(required = false, name = "lat") Float latitude,
            @RequestParam(required = false, name = "lon") Float longitude
    ) {
        if (city != null) {
            return weatherService.getCurrentByCity(city);
        } else if (latitude != null && longitude != null) {
            return weatherService.getCurrentByCoordinates(Coordinate.of(latitude, longitude));
        } else {
            throw new IllegalArgumentException("Either city or latitude and longitude must be provided.");
        }
    }

    @PostMapping("/gen-sample")
    public String generateSampleData(
            @RequestParam int n
    ) {
        weatherService.generateSampleData(n);
        return "ok";
    }
}
