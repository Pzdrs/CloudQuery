package cz.pycrs.cloudquery.service.impl;

import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;


    @Override
    public void deletePlace(int id) {
        log.info("Deleting place with ID: {}", id);
        placeRepository.deleteById(id);
    }

    @Override
    public Place getPlace(int id) {
        return placeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Place not found"));
    }

    @Override
    public List<Place> getPlaces() {
        return placeRepository.findAll();
    }
}
