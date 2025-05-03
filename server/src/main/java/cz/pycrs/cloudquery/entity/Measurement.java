package cz.pycrs.cloudquery.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private double rainIntensity;

    public Measurement(
            Place place, Instant timestamp,
            double temperature, double feelsLike, double minTemperature, double maxTemperature,
            double pressureSeaLevel, double pressureGroundLevel,
            double humidity, double rainIntensity
    ) {
        this.place = place;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.pressureSeaLevel = pressureSeaLevel;
        this.pressureGroundLevel = pressureGroundLevel;
        this.humidity = humidity;
        this.rainIntensity = rainIntensity;
    }
}
