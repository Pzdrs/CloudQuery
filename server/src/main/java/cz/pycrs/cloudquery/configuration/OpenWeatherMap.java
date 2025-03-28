package cz.pycrs.cloudquery.configuration;

import io.github.mbenincasa.javaopenweathermapclient.client.DefaultOpenWeatherMapClient;
import io.github.mbenincasa.javaopenweathermapclient.client.OpenWeatherMapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenWeatherMap {
    @Value("${openweathermap.apikey}")
    private String apiKey;

    @Bean
    public OpenWeatherMapClient openWeatherMapClient() {
        return new DefaultOpenWeatherMapClient(apiKey);
    }
}
