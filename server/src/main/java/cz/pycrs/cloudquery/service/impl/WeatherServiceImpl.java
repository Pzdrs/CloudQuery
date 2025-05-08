package cz.pycrs.cloudquery.service.impl;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.request.weather.single.SingleResultCurrentWeatherRequestCustomizer;
import cz.pycrs.cloudquery.dto.MeasurementAverages;
import cz.pycrs.cloudquery.dto.MeasurementPatchRequest;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.service.PlaceService;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherMapClient owmClient;
    private final PlaceService placeService;
    private final MeasurementRepository measurementRepository;


    @Override
    public Measurement getCurrentForCity(String city) {
        log.info("Fetching current weather for city: {}", city);
        return createMeasurement(owmClient.currentWeather().single().byCityName(city));
    }

    @Override
    public Measurement getCurrentForCoordinates(Coordinate coordinate) {
        log.info("Getting current weather data records for {}", coordinate);
        return createMeasurement(owmClient.currentWeather().single().byCoordinate(coordinate));
    }

    @Override
    public Page<Measurement> getAllForPlace(int id, Pageable pageable) {
        return measurementRepository.findAllByPlace(placeService.getPlace(id), pageable);
    }

    @Override
    public void deleteMeasurement(int id) {
        log.info("Deleting measurement with ID: {}", id);
        var measurement = getMeasurement(id);
        measurementRepository.delete(measurement);
    }

    @Override
    public Measurement getMeasurement(int id) {
        return measurementRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Measurement with ID " + id + " not found"));
    }

    @Override
    public Page<Measurement> getAllMeasurements(Pageable pageable) {
        return measurementRepository.findAll(pageable);
    }

    @Override
    public Measurement updateMeasurement(int id, MeasurementPatchRequest patch) {
        var measurement = getMeasurement(id);

        if (patch.placeId() != null) measurement.setPlace(placeService.getPlace(patch.placeId()));
        if (patch.timestamp() != null) measurement.setTimestamp(patch.timestamp());
        if (patch.temperature() != null) measurement.setTemperature(patch.temperature());
        if (patch.feelsLike() != null) measurement.setFeelsLike(patch.feelsLike());
        if (patch.minTemperature() != null) measurement.setMinTemperature(patch.minTemperature());
        if (patch.maxTemperature() != null) measurement.setMaxTemperature(patch.maxTemperature());
        if (patch.pressureGroundLevel() != null) measurement.setPressureGroundLevel(patch.pressureGroundLevel());
        if (patch.pressureSeaLevel() != null) measurement.setPressureSeaLevel(patch.pressureSeaLevel());
        if (patch.humidity() != null) measurement.setHumidity(patch.humidity());

        return measurementRepository.save(measurement);
    }

    @Override
    public MeasurementAverages getAveragesForPlace(int id, int days) {
        return measurementRepository.findAveragesByPlace(placeService.getPlace(id));
    }


    private Measurement createMeasurement(SingleResultCurrentWeatherRequestCustomizer customizer) {
        var response = customizer
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        var loc = response.getLocation();
        var place = placeService.getOrCreatePlace(
                loc.getId(), loc.getName(), loc.getCountryCode(), loc.getZoneOffset(), null
        );

        return measurementRepository.save(new Measurement(
                place, response.getCalculationTime().toInstant(place.getZoneOffset())
        ).ofWeather(response));
    }
}
