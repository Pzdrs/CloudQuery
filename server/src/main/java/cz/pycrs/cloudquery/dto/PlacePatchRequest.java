package cz.pycrs.cloudquery.dto;

import java.time.ZoneOffset;

public record PlacePatchRequest(
        String cityName,
        String countryCode,
        ZoneOffset zoneOffset,
        String comment
) {
}
