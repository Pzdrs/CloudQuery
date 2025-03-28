package cz.pycrs.cloudquery;

import cz.pycrs.cloudquery.repository.OWMResponseRepository;
import io.github.mbenincasa.javaopenweathermapclient.client.DefaultOpenWeatherMapClient;
import io.github.mbenincasa.javaopenweathermapclient.client.OpenWeatherMapClient;
import io.github.mbenincasa.javaopenweathermapclient.request.common.Lang;
import io.github.mbenincasa.javaopenweathermapclient.request.common.Unit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudQueryApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(OpenWeatherMapClient openWeatherMapClient, OWMResponseRepository responseRepository) {
        return args -> {
            System.out.println(openWeatherMapClient.oneCallApi().overview(50.0, 50.0).response());
        };
    }
}
