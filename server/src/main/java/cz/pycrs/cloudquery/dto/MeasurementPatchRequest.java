package cz.pycrs.cloudquery.dto;

import java.time.Instant;

public record MeasurementPatchRequest(
        Integer placeId,
        Instant timestamp,
        Double temperature,
        Double feelsLike,
        Double minTemperature,
        Double maxTemperature,
        Double pressureSeaLevel,
        Double pressureGroundLevel,
        Double humidity
) {
}
