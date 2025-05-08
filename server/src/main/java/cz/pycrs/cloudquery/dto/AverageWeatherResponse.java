package cz.pycrs.cloudquery.dto;

import cz.pycrs.cloudquery.entity.Place;

public record AverageWeatherResponse(
        Place place,
        int days
) {
}
