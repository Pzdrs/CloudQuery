package cz.pycrs.cloudquery.service;

import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;

import java.util.List;

public interface WeatherService {
    void generateSampleData(int n);

    List<Place> getPlaces();

    Measurement getCurrentByCity(String city);
    Measurement getCurrentByCoordinates(Coordinate coordinate);
}
