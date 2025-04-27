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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherServiceImpl implements WeatherService {
    private final OpenWeatherMapClient owmClient;
    private final MeasurementRepository measurementRepository;
    private final PlaceRepository placeRepository;

    @Override
    public void generateSampleData(int n) {
        log.info("Generating {} sample weather data records.", n);
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
