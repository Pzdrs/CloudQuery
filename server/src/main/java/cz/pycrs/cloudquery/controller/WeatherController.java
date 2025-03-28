package cz.pycrs.cloudquery.controller;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherMapClient owmClient;

    @GetMapping("/current")
    public String currentWeather(
            @RequestParam(required = false) String city,
            @RequestParam(required = false, name = "lat") Double latitude,
            @RequestParam(required = false, name = "lon") Double longitude
    ) {
        if (city != null) {
            return owmClient.currentWeather().single().byCityName(city).retrieve().asJSON();
        } else if (latitude != null && longitude != null) {
            return owmClient.currentWeather().single().byCoordinate(Coordinate.of(latitude, longitude)).retrieve().asJSON();
        } else {
            throw new IllegalArgumentException("Either city or latitude and longitude must be provided.");
        }
    }
}
