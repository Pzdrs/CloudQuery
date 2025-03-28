package cz.pycrs.cloudquery;

import cz.pycrs.cloudquery.entity.Country;
import cz.pycrs.cloudquery.repository.CountryRepository;
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
    CommandLineRunner init(CountryRepository countryRepository) {
        return args -> {
          countryRepository.save(new Country("CZ", "Czech Republic"));
        };
    }
}
