package cz.pycrs.cloudquery.entity;


import com.github.prominence.openweathermap.api.model.weather.Weather;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Measurement {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Place place;

    private Instant timestamp;
    private double temperature;
    private double feelsLike;
    private double minTemperature, maxTemperature;
    private double pressureSeaLevel, pressureGroundLevel;
    private double humidity;

    public Measurement(
            Place place, Instant timestamp,
            Weather weather
    ) {
        var temp =  weather.getTemperature();
        var pressure = weather.getAtmosphericPressure();

        this.place = place;
        this.timestamp = timestamp;
        this.temperature = temp.getValue();
        this.feelsLike = temp.getFeelsLike();
        this.minTemperature = temp.getMinTemperature();
        this.maxTemperature = temp.getMaxTemperature();
        this.pressureSeaLevel = pressure.getSeaLevelValue();
        this.pressureGroundLevel = pressure.getGroundLevelValue();
        this.humidity = weather.getHumidity().getValue();
    }
}
