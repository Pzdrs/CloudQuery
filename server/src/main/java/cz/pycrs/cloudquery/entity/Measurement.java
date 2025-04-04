package cz.pycrs.cloudquery.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class Measurement {
    protected Measurement() {
    }

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Place place;

    private Instant timestamp;
    private double temperature;
    private double feelsLike;
    private double minTemperature, maxTemperature;
    private double pressureSeaLevel, pressureGroundLevel;
    private double humidity;

    public Measurement(
            Place place, Instant timestamp,
            double temperature, double feelsLike, double minTemperature, double maxTemperature,
            double pressureSeaLevel, double pressureGroundLevel,
            double humidity
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
    }
}
