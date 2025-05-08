package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MeasurementRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    PlaceRepository placeRepository;

    /**
     * Ty printy tam musej bejt jinak ty testy nefungujou MEGA NECHAPU CO SE DEJE
     */
    @BeforeEach
    void setUp() {
        var places = List.of(
                new Place(1, "Liberec", "CZ", ZoneOffset.ofHours(2), null),
                new Place(2, "Mšeno", "CZ", ZoneOffset.ofHours(2), null),
                new Place(3, "Mladá Boleslav", "CZ", ZoneOffset.ofHours(2), null)
        );
        placeRepository.saveAll(places);

        System.out.println(placeRepository.findAll());

        var measurements = List.of(
                new Measurement(places.get(0), Instant.now(), 10d, 10d, 10d, 10d, 1000d, 1000d, 10),
                new Measurement(places.get(1), Instant.now().minus(Period.ofDays(2)), 20d, 20d, 20d, 20d, 2000d, 2000d, 20),
                new Measurement(places.get(2), Instant.now().minus(Period.ofDays(3)), 30d, 30d, 30d, 30d, 3000d, 3000d, 30)
        );
        measurementRepository.saveAll(measurements);

        System.out.println(measurementRepository.findAll());
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void correctlySetup() {
        assertThat(placeRepository.findAll()).hasSize(3);
        assertThat(measurementRepository.findAll()).hasSize(3);
    }


    @Test
    void testFindAllByPlace() {
        assertThat(measurementRepository.findAllByPlace(placeRepository.findById(1).orElseThrow(), Pageable.ofSize(10)).getContent()).hasSize(1);
    }

    @Test
    void testFindAveragesByPlace() {
        var place = placeRepository.findById(1).orElseThrow();
        var averages = measurementRepository.findAveragesByPlace(place);

        assertThat(averages).isNotNull();
        assertThat(averages.temperature()).isEqualTo(10d);
        assertThat(averages.feelsLike()).isEqualTo(10d);
        assertThat(averages.minTemperature()).isEqualTo(10d);
        assertThat(averages.maxTemperature()).isEqualTo(10d);
        assertThat(averages.pressureGroundLevel()).isEqualTo(1000d);
        assertThat(averages.pressureSeaLevel()).isEqualTo(1000d);
        assertThat(averages.humidity()).isEqualTo(10);
    }
}