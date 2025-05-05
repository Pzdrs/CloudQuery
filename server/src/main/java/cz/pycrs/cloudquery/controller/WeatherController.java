package cz.pycrs.cloudquery.controller;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final OpenWeatherMapClient owmClient;
    private final WeatherService weatherService;

    @GetMapping("/current")
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
    public Measurement currentWeather(
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

    @PostMapping("/gen-sample")
    @Operation(
            summary = "Vygenerovat vzorová data pro dané místo",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "amount", description = "Počet vzorových dat"),
                    @io.swagger.v3.oas.annotations.Parameter(name = "place_id", description = "ID místa")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "204",
                            description = "Úspěšně vygenerováno **n** vzorových dat"
                    )
            }
    )
    public ResponseEntity<?> generateSampleData(
            @RequestParam("amount") int n,
            @RequestParam(value = "place_id") int placeId
    ) {
        weatherService.generateSampleData(n, placeId);
        return ResponseEntity.status(204).build();
    }
}
