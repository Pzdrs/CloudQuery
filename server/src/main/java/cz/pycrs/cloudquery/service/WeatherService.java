package cz.pycrs.cloudquery.service;

import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.entity.Measurement;

public interface WeatherService {

    /**
     * Generate sample data for the given place ID
     *
     * @param n       number of measurements to generate
     * @param placeId ID of the place to generate data for
     */
    void generateSampleData(int n, int placeId);

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

}
