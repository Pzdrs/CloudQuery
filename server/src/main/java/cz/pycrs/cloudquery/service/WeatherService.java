package cz.pycrs.cloudquery.service;

import com.github.prominence.openweathermap.api.model.Coordinate;
import cz.pycrs.cloudquery.dto.MeasurementAverages;
import cz.pycrs.cloudquery.dto.MeasurementPatchRequest;
import cz.pycrs.cloudquery.entity.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

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
    Page<Measurement> getAllForPlace(int id, Pageable pageable);

    /**
     * Delete the measurement with the given ID
     *
     * @param id ID of the measurement to delete
     */
    void deleteMeasurement(int id);

    /**
     * Get the measurement with the given ID
     *
     * @param id ID of the measurement to get
     * @return measurement with the given ID
     */
    Measurement getMeasurement(int id);

    /**
     * Get all measurements
     *
     * @return all measurements
     */
    Page<Measurement> getAllMeasurements(Pageable pageable);

    /**
     * Update the measurement with the given ID
     *
     * @param id ID of the measurement to update
     * @return updated measurement
     */
    Measurement updateMeasurement(int id, MeasurementPatchRequest patch);

    /**
     * Get the average measurements for the given place ID
     *
     * @param id   place ID to get the averages for
     * @param days number of days to get the averages for
     * @return average measurements for the given place ID
     */
    MeasurementAverages getAveragesForPlace(int id, int days);
}
