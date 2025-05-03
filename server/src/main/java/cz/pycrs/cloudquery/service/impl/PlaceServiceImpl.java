package cz.pycrs.cloudquery.service.impl;

import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.service.PlaceService;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
    private final MeasurementRepository measurementRepository;

    /**
     * @param intensity rain intensity
     * @param from      from date
     * @param to        to date
     * @return all places where the rain intensity was equal to the given value in the given date range
     */
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
}
