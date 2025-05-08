package cz.pycrs.cloudquery.entity;


import com.github.prominence.openweathermap.api.model.weather.Weather;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NonNull
    private Place place;

    @NonNull
    private Instant timestamp;
    @NonNull
    private Double temperature;
    @NonNull
    private Double feelsLike;
    @NonNull
    private Double minTemperature, maxTemperature;
    @NonNull
    private Double pressureSeaLevel, pressureGroundLevel;
    @NonNull
    private Integer humidity;

    public Measurement(@NonNull Place place, @NonNull Instant timestamp) {
        this.place = place;
        this.timestamp = timestamp;
    }

    public Measurement ofWeather(Weather weather) {
        var temp = weather.getTemperature();
        var pressure = weather.getAtmosphericPressure();

        this.temperature = temp.getValue();
        this.feelsLike = temp.getFeelsLike();
        this.minTemperature = temp.getMinTemperature();
        this.maxTemperature = temp.getMaxTemperature();
        this.pressureSeaLevel = pressure.getSeaLevelValue();
        this.pressureGroundLevel = pressure.getGroundLevelValue();
        this.humidity = weather.getHumidity().getValue();

        return this;
    }
}
