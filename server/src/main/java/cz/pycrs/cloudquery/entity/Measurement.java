package cz.pycrs.cloudquery.entity;


import com.github.prominence.openweathermap.api.enums.WeatherCondition;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.weather.Weather;
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
    private WeatherCondition weatherCondition;

    public Measurement(
            Place place, Instant timestamp,
            Weather weather
    ) {
        var temp =  weather.getTemperature();
        var pressure = weather.getAtmosphericPressure();
        var rain = weather.getRain();

        this.place = place;
        this.timestamp = timestamp;
        this.temperature = temp.getValue();
        this.feelsLike = temp.getFeelsLike();
        this.minTemperature = temp.getMinTemperature();
        this.maxTemperature = temp.getMaxTemperature();
        this.pressureSeaLevel = pressure.getSeaLevelValue();
        this.pressureGroundLevel = pressure.getGroundLevelValue();
        this.humidity = weather.getHumidity().getValue();
        this.rainIntensity = rain == null ? 0 : rain.getOneHourLevel();
        this.weatherCondition = weather.getWeatherState().getWeatherConditionEnum();
    }
}
