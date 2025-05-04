package cz.pycrs.cloudquery.service.impl;

import cz.pycrs.cloudquery.dto.PlaceTemperatureDifference;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.PlaceService;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {
    private final MeasurementRepository measurementRepository;
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

    @Override
    public List<Place> commonRainIntensity(double intensity, @NonNull ZonedDateTime from, @Nullable ZonedDateTime to) {
        return measurementRepository.findAllByRainIntensity(intensity).stream().filter(measurement -> {
            ZonedDateTime measurementTime = ZonedDateTime.ofInstant(measurement.getTimestamp(), ZoneOffset.UTC);
            // if only from is provided only measurements from that DAY, otherwise all measurements in range
            if (to == null) {
                return measurementTime.toLocalDate().equals(from.toLocalDate());
            } else {
                return !measurementTime.isBefore(from) && !measurementTime.isAfter(to);
            }
        }).map(Measurement::getPlace).distinct().toList();
    }

    @Override
    public List<PlaceTemperatureDifference> largestTemperatureDifference(ZonedDateTime targetDate, @Nullable Integer n) {
        return placeRepository
                .findTempDiffsByDate(targetDate.toLocalDate()).stream()
                .map(diff -> {
                    var place = placeRepository.findById(diff.placeId()).orElseThrow();
                    return new PlaceTemperatureDifference(
                            place,
                            diff.maxTemp(),
                            diff.minTemp(),
                            diff.largestTempDiff()
                    );
                }).limit(n == null ? Long.MAX_VALUE : n).toList();
    }
}
