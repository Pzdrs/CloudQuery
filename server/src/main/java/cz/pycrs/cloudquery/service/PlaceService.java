package cz.pycrs.cloudquery.service;

import cz.pycrs.cloudquery.dto.PlacePatchRequest;
import cz.pycrs.cloudquery.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZoneOffset;
import java.util.List;

public interface PlaceService {

    /**
     * Get place by ID
     *
     * @return all places
     */
    Page<Place> getPlaces(Pageable pageable);

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
     * Get or create place by city name, country code and zone offset
     *
     * @param cityName    city name
     * @param countryCode country code
     * @param offset      zone offset
     * @param comment     comment
     * @return place with the given parameters
     */
    Place getOrCreatePlace(int id, String cityName, String countryCode, ZoneOffset offset, String comment);

    /**
     * Update place by ID
     *
     * @param id place ID
     * @return updated place
     */
    Place updatePlace(int id, PlacePatchRequest patch);
}
