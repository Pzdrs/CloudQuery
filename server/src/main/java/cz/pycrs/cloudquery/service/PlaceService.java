package cz.pycrs.cloudquery.service;

import cz.pycrs.cloudquery.dto.PlaceTemperatureDifference;
import cz.pycrs.cloudquery.entity.Place;
import jakarta.annotation.Nullable;

import java.time.ZonedDateTime;
import java.util.List;

public interface PlaceService {

    /**
     * Get place by ID
     *
     * @return all places
     */
    List<Place> getPlaces();

    /**
     * Delete place by ID
     *
     * @param id place ID
     */
    void deletePlace(int id);

    /**
     * Get place by ID
     *
     * @param id place ID
     * @return place with the given ID
     */
    Place getPlace(int id);

    /**
     * Find all places where the rain intensity was equal to the given value in the given date range
     *
     * @param intensity rain intensity
     * @param from      from date
     * @param to        to date
     * @return all places where the rain intensity was equal to the given value in the given date range
     */
    List<Place> commonRainIntensity(double intensity, ZonedDateTime from, ZonedDateTime to);

    /**
     * Find all places where the temperature difference was the highest in the given date
     *
     * @param targetDate date to find the highest temperature difference
     * @param n          number of top places to return
     * @return all places where the temperature difference was the highest in the given date
     */
    List<PlaceTemperatureDifference> largestTemperatureDifference(ZonedDateTime targetDate, @Nullable Integer n);
}
