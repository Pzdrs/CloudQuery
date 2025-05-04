package cz.pycrs.cloudquery.dto;

import cz.pycrs.cloudquery.entity.Place;

public record PlaceTemperatureDifference(
        Place place,
        double minTemperature,
        double maxTemperature,
        double diff
) {
}
