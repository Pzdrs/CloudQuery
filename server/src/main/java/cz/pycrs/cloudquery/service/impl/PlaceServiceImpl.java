package cz.pycrs.cloudquery.service.impl;

import cz.pycrs.cloudquery.dto.PlacePatchRequest;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
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
    public Place getOrCreatePlace(int id, String cityName, String countryCode, ZoneOffset offset, String comment) {
        log.info("{} ({}) not found in DB, creating new entry.", cityName, id);
        return placeRepository.save(Place.builder()
                .id(id)
                .cityName(cityName)
                .countryCode(countryCode)
                .zoneOffset(offset)
                .comment(comment)
                .build());
    }

    @Override
    public Place updatePlace(int id, PlacePatchRequest patch) {
        log.info("Updating place with ID: {}", id);
        var place = placeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Place not found"));
        if (patch.cityName() != null && !patch.cityName().isEmpty()) place.setCityName(patch.cityName());
        if (patch.countryCode() != null && !patch.countryCode().isEmpty()) place.setCountryCode(patch.countryCode());
        if (patch.zoneOffset() != null) place.setZoneOffset(patch.zoneOffset());
        if (patch.comment() != null && !patch.comment().isEmpty()) place.setComment(patch.comment());
        return placeRepository.save(place);
    }

    @Override
    public Page<Place> getPlaces(Pageable pageable) {
        return placeRepository.findAll(pageable);
    }
}
