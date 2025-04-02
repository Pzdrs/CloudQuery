package cz.pycrs.cloudquery.controller;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherMapClient owmClient;
    private final WeatherService weatherService;

    @GetMapping("/current")
    public String currentWeather(
            @RequestParam(required = false) String city,
            @RequestParam(required = false, name = "lat") Double latitude,
            @RequestParam(required = false, name = "lon") Double longitude
    ) {
        if (city != null) {
            return owmClient.currentWeather().single().byCityName(city).retrieve().asJSON();
        } else if (latitude != null && longitude != null) {
            return owmClient.oneCall3().historical().byCoordinateAndTimestamp(Coordinate.of(latitude, longitude), 1620000000).retrieve().asJSON();
            //return owmClient.currentWeather().single().byCoordinate(Coordinate.of(latitude, longitude)).retrieve().asJSON();
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
