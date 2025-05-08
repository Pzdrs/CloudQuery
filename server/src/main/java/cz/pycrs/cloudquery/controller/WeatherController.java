package cz.pycrs.cloudquery.controller;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherMapClient owmClient;
    private final WeatherService weatherService;

    @GetMapping("/fetch-current")
    @Operation(
            summary = "Získat aktuální počasí podle města nebo souřadnic",
            description = "Zadejte buď název města (`city`), nebo kombinaci `lat` a `lon`.",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "city",
                            description = "Název města. **Povinné, pokud nejsou zadány souřadnice.**"
                    ),
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "lat",
                            description = "Zeměpisná šířka. **Povinná, pokud není zadáno město.**"
                    ),
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "lon",
                            description = "Zeměpisná délka. **Povinná, pokud není zadáno město.**"
                    )
            }
    )
    public Measurement fetchCurrentWeather(
            @RequestParam(required = false) String city,
            @RequestParam(required = false, name = "lat") Float latitude,
            @RequestParam(required = false, name = "lon") Float longitude
    ) {
        if (city != null) {
            return weatherService.getCurrentForCity(city);
        } else if (latitude != null && longitude != null) {
            return weatherService.getCurrentForCoordinates(Coordinate.of(latitude, longitude));
        } else {
            throw new IllegalArgumentException("Either city or latitude and longitude must be provided.");
        }
    }

    @GetMapping
    @Operation(
            summary = "Získat aktuální počasí místa podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "id",
                            description = "ID místa."
                    )
            }
    )
    public List<Measurement> currentWeather(
            @RequestParam int id,
            @RequestParam(required = false) Integer limit
    ) {
        return weatherService.getAllForPlace(id, limit);
    }

    @GetMapping("/average")
    @Operation(
            summary = "Získat průměrné počasí místa za posledních n dní",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "id",
                            description = "ID místa."
                    ),
                    @io.swagger.v3.oas.annotations.Parameter(
                            name = "days",
                            description = "Počet dní pro průměr."
                    )
            }
    )
    public void averageWeather(
            @RequestParam int id,
            @RequestParam int days
    ) {}
}
