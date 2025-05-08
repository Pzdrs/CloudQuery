package cz.pycrs.cloudquery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MeasurementAverages(
        @JsonProperty("avgTemp") double temperature,
        @JsonProperty("avgFeelsLike") double feelsLike,
        @JsonProperty("avgMinTemp") double minTemperature,
        @JsonProperty("avgMaxTemp") double maxTemperature,
        @JsonProperty("avgPressureGnd") double pressureGroundLevel,
        @JsonProperty("avgPressureSea") double pressureSeaLevel,
        @JsonProperty("avgHumidity") double humidity
) {
}
