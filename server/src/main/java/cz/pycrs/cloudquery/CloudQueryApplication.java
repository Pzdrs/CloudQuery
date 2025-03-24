package cz.pycrs.cloudquery;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
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
    public CommandLineRunner run(OpenWeatherMapClient client) {
        return args -> {
            System.out.println(client.currentWeather().single().byCityName("Liberec").retrieve().asJSON());
        };
    }
}
