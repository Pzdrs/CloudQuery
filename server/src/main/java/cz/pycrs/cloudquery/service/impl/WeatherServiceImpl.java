package cz.pycrs.cloudquery.service.impl;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Coordinate;
import com.github.prominence.openweathermap.api.request.weather.single.SingleResultCurrentWeatherRequestCustomizer;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.WeatherService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherMapClient owmClient;
    private final MeasurementRepository measurementRepository;
    private final PlaceRepository placeRepository;


    @Override
    public void generateSampleData(int n, int placeId) {
        log.info("Generating {} sample weather data records.", n);

        var place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place with ID " + placeId + " not found."));

        for (int i = 0; i < n; i++) {
            var measurement = new Measurement(
                    place,
                    Instant.now(),
                    20.0f + (float) (Math.random() * 10), // Random temperature between 20 and 30
                    20.0f + (float) (Math.random() * 10), // Random feels like temperature
                    20.0f + (float) (Math.random() * 10), // Random min temperature
                    30.0f + (float) (Math.random() * 10), // Random max temperature
                    1000.0f + (float) (Math.random() * 50), // Random sea level pressure
                    1000.0f + (float) (Math.random() * 50), // Random ground level pressure
                    50.0f + (float) (Math.random() * 50) // Random humidity
            );
            measurementRepository.save(measurement);
        }
    }

    @Override
    public List<Place> getPlaces() {
        return placeRepository.findAll();
    }

    @Override
    public Measurement getCurrentByCity(String city) {
        log.info("Fetching current weather for city: {}", city);
        return createMeasurement(owmClient.currentWeather().single().byCityName(city));
    }

    @Override
    public Measurement getCurrentByCoordinates(Coordinate coordinate) {
        log.info("Getting current weather data records for {}", coordinate);
        return createMeasurement(owmClient.currentWeather().single().byCoordinate(coordinate));
    }

    @Override
    public void deletePlace(int id) {
        log.info("Deleting place with ID: {}", id);
        placeRepository.deleteById(id);
    }

    private Measurement createMeasurement(SingleResultCurrentWeatherRequestCustomizer customizer) {
        var response = customizer
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        var loc = response.getLocation();
        var place = placeRepository.findById(loc.getId()).orElseGet(() -> {
            log.info("{} ({}) not found in DB, creating new entry.", loc.getName(), loc.getId());
            var newPlace = new Place(
                    loc.getId(),
                    loc.getName(),
                    loc.getCountryCode(),
                    loc.getZoneOffset(),
                    loc.getCoordinate().getLatitude(),
                    loc.getCoordinate().getLongitude()
            );
            return placeRepository.save(newPlace);
        });

        var temp = response.getTemperature();
        var pressure = response.getAtmosphericPressure();
        return measurementRepository.save(new Measurement(
                place, response.getCalculationTime().toInstant(place.getZoneOffset()),
                temp.getValue(), temp.getFeelsLike(), temp.getMinTemperature(), temp.getMaxTemperature(),
                pressure.getSeaLevelValue(), pressure.getGroundLevelValue(), response.getHumidity().getValue()
        ));
    }
}
