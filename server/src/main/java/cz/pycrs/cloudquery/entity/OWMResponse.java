package cz.pycrs.cloudquery.entity;

import com.github.prominence.openweathermap.api.model.weather.Weather;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "responses")
public class OWMResponse extends Weather {
    @Id
    private ObjectId _id;

    public OWMResponse(Weather weather) {
        setCalculationTime(weather.getCalculationTime());
        setWeatherState(weather.getWeatherState());
        setTemperature(weather.getTemperature());
        setAtmosphericPressure(weather.getAtmosphericPressure());
        setHumidity(weather.getHumidity());
        setWind(weather.getWind());
        setRain(weather.getRain());
        setSnow(weather.getSnow());
        setClouds(weather.getClouds());
        setLocation(weather.getLocation());
    }
}