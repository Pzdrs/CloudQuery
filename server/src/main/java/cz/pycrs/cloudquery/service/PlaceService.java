package cz.pycrs.cloudquery.service;

import cz.pycrs.cloudquery.entity.Place;

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
}
