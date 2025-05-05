package cz.pycrs.cloudquery.service.impl;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.*;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.github.prominence.openweathermap.api.request.weather.single.SingleResultCurrentWeatherRequestCustomizer;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.OWMResponse;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.repository.OWMResponseRepository;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherMapClient owmClient;
    private final MeasurementRepository measurementRepository;
    private final PlaceRepository placeRepository;
    private final OWMResponseRepository owmResponseRepository;


    @Override
    public void generateSampleData(int n, int placeId) {
        log.info("Generating {} sample weather data records.", n);

        var place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Place with ID " + placeId + " not found."));

        for (int i = 0; i < n; i++) {
            var weather = new Weather();
            var temp = Temperature.withValue(23.5, "C");
            var pressure = AtmosphericPressure.withValue(1000);

            temp.setFeelsLike(20d);
            temp.setMinTemperature(17d);
            temp.setMaxTemperature(30d);
            pressure.setGroundLevelValue(950);
            pressure.setSeaLevelValue(1000);

            weather.setTemperature(temp);
            weather.setHumidity(Humidity.withValue((byte) 57));

            weather.setWeatherState(new WeatherState(804, "Clouds", "overcast clouds"));
            weather.setAtmosphericPressure(pressure);

            var measurement = new Measurement(
                    place,
                    Instant.now(),
                    weather
            );
            measurementRepository.save(measurement);
        }
    }


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


    private Measurement createMeasurement(SingleResultCurrentWeatherRequestCustomizer customizer) {
        var response = customizer
                .unitSystem(UnitSystem.METRIC)
                .retrieve()
                .asJava();

        owmResponseRepository.save(new OWMResponse(response));

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

        return measurementRepository.save(new Measurement(
                place, response.getCalculationTime().toInstant(place.getZoneOffset()), response
        ));
    }
}
