package cz.pycrs.cloudquery.controller;

import cz.pycrs.cloudquery.dto.MeasurementPatchRequest;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather/measurement")
@RequiredArgsConstructor
public class MeasurementController {
    private final WeatherService weatherService;

    @GetMapping("/list")
    @Operation(
            summary = "Získat všechna měření"
    )
    public Page<Measurement> list(
            Pageable pageable
    ) {
        return weatherService.getAllMeasurements(pageable);
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Získat měření podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID měření")
            }
    )
    public Measurement getMeasurementById(
            @PathVariable int id
    ) {
        return weatherService.getMeasurement(id);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Smazat měření podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID měření")
            }
    )
    public ResponseEntity<?> deleteMeasurement(
            @PathVariable int id
    ) {
        weatherService.deleteMeasurement(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Aktualizovat měření podle ID",
            parameters = {
                    @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID měření")
            }
    )
    public Measurement updateMeasurement(
            @PathVariable int id,
            @RequestBody MeasurementPatchRequest patch
    ) {
        return weatherService.updateMeasurement(id, patch);
    }
}
