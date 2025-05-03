package cz.pycrs.cloudquery.service;

import cz.pycrs.cloudquery.entity.Place;

import java.time.ZonedDateTime;
import java.util.List;

public interface PlaceService {
    List<Place> commonRainIntensity(double intensity, ZonedDateTime from, ZonedDateTime to);
}
