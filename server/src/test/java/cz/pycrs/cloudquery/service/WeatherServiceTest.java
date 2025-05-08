package cz.pycrs.cloudquery.service;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.model.AtmosphericPressure;
import com.github.prominence.openweathermap.api.model.Humidity;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.weather.Location;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import cz.pycrs.cloudquery.dto.MeasurementAverages;
import cz.pycrs.cloudquery.dto.MeasurementPatchRequest;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.service.impl.WeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    OpenWeatherMapClient owmClient;

    @Mock
    PlaceService placeService;

    @Mock
    MeasurementRepository measurementRepository;

    @InjectMocks
    WeatherServiceImpl weatherService;

    Place samplePlace;
    Measurement sampleMeasurement;

    @BeforeEach
    void setUp() {
        samplePlace = Place.builder()
                .id(1)
                .cityName("Liberec")
                .countryCode("CZ")
                .zoneOffset(ZoneOffset.ofHours(2))
                .build();

        sampleMeasurement = new Measurement(samplePlace, Instant.now());
    }

    @Test
    void testGetCurrentForCity() {
        var response = mock(Weather.class);
        var loc = mock(Location.class);
        var temperature = mock(Temperature.class);
        var pressure = mock(AtmosphericPressure.class);
        var humidity = mock(Humidity.class);

        when(owmClient.currentWeather().single().byCityName("Liberec")
                .unitSystem(any()).retrieve().asJava()).thenReturn(response);

        when(response.getLocation()).thenReturn(loc);
        when(response.getTemperature()).thenReturn(temperature);
        when(response.getAtmosphericPressure()).thenReturn(pressure);
        when(response.getHumidity()).thenReturn(humidity);

        when(humidity.getValue()).thenReturn(50);

        when(loc.getId()).thenReturn(1);
        when(loc.getName()).thenReturn("Liberec");
        when(loc.getCountryCode()).thenReturn("CZ");
        when(loc.getZoneOffset()).thenReturn(ZoneOffset.ofHours(2));

        when(temperature.getValue()).thenReturn(20.0);

        when(pressure.getSeaLevelValue()).thenReturn(1000.0);

        when(response.getCalculationTime()).thenReturn(ZonedDateTime.now().toLocalDateTime());

        when(placeService.getOrCreatePlace(anyInt(), any(), any(), any(), any())).thenReturn(samplePlace);
        when(measurementRepository.save(any())).thenReturn(sampleMeasurement);

        Measurement result = weatherService.getCurrentForCity("Liberec");

        assertThat(result).isEqualTo(sampleMeasurement);
        verify(measurementRepository).save(any(Measurement.class));
    }

    @Test
    void testGetAllForPlace() {
        Pageable pageable = PageRequest.of(0, 10);
        when(placeService.getPlace(1)).thenReturn(samplePlace);
        when(measurementRepository.findAllByPlace(samplePlace, pageable))
                .thenReturn(new PageImpl<>(List.of(sampleMeasurement)));

        Page<Measurement> result = weatherService.getAllForPlace(1, pageable);

        assertThat(result.getContent()).containsExactly(sampleMeasurement);
    }

    @Test
    void testDeleteMeasurement() {
        when(measurementRepository.findById(1)).thenReturn(Optional.of(sampleMeasurement));

        weatherService.deleteMeasurement(1);

        verify(measurementRepository).delete(sampleMeasurement);
    }

    @Test
    void testGetMeasurement_found() {
        when(measurementRepository.findById(1)).thenReturn(Optional.of(sampleMeasurement));

        Measurement result = weatherService.getMeasurement(1);

        assertThat(result).isEqualTo(sampleMeasurement);
    }

    @Test
    void testGetMeasurement_notFound() {
        when(measurementRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> weatherService.getMeasurement(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Measurement with ID 1 not found");
    }

    @Test
    void testGetAllMeasurements() {
        Pageable pageable = PageRequest.of(0, 5);
        when(measurementRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(sampleMeasurement)));

        Page<Measurement> result = weatherService.getAllMeasurements(pageable);

        assertThat(result.getContent()).containsExactly(sampleMeasurement);
    }

    @Test
    void testUpdateMeasurement() {
        MeasurementPatchRequest patch = new MeasurementPatchRequest(
                2, Instant.now(), 22.0, 21.0, 20.0, 25.0, 1010.0, 1005.0, 60
        );

        Place newPlace = Place.builder().id(2).cityName("Brno").countryCode("CZ").zoneOffset(ZoneOffset.ofHours(1)).build();

        when(measurementRepository.findById(1)).thenReturn(Optional.of(sampleMeasurement));
        when(placeService.getPlace(2)).thenReturn(newPlace);
        when(measurementRepository.save(any())).thenReturn(sampleMeasurement);

        Measurement result = weatherService.updateMeasurement(1, patch);

        assertThat(result).isEqualTo(sampleMeasurement);
        verify(measurementRepository).save(sampleMeasurement);
    }

    @Test
    void testGetAveragesForPlace() {
        MeasurementAverages averages = new MeasurementAverages(20.0, 22.0, 18.0, 1000.0, 1005.0, 50, 55.0);
        when(measurementRepository.findAveragesByPlace(samplePlace)).thenReturn(averages);
        when(placeService.getPlace(1)).thenReturn(samplePlace);
        when(measurementRepository.findAveragesByPlace(samplePlace)).thenReturn(averages);

        MeasurementAverages result = weatherService.getAveragesForPlace(1, 7);

        assertThat(result).isEqualTo(averages);
    }
}