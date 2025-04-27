package cz.pycrs.cloudquery;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class CloudQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudQueryApplication.class, args);


    }

    @Bean
    ApplicationRunner init(Environment environment) {
        return args -> {
            System.out.println(Arrays.toString(environment.getActiveProfiles()));
        };
    }
}
