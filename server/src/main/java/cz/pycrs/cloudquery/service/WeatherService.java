package cz.pycrs.cloudquery.service;

import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.entity.Measurement;

import java.util.List;

public interface WeatherService {
    /**
     * Get the current weather for the given place ID
     *
     * @param city name of the city to get the weather for
     * @return current weather for the given place ID
     */
    Measurement getCurrentForCity(String city);

    /**
     * Get the current weather for the given coordinates
     *
     * @param coordinate coordinates to get the weather for
     * @return current weather for the given coordinates
     */
    Measurement getCurrentForCoordinates(Coordinate coordinate);

    /**
     * Get the current weather for the given place ID
     *
     * @param id place ID to get the weather for
     * @return current weather for the given place ID
     */
    List<Measurement> getAllForPlace(int id, Integer limit);
}
