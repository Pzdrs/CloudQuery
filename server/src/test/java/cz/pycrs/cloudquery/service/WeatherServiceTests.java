package cz.pycrs.cloudquery.service;

import cz.pycrs.cloudquery.entity.Place;
import cz.pycrs.cloudquery.repository.MeasurementRepository;
import cz.pycrs.cloudquery.repository.PlaceRepository;
import cz.pycrs.cloudquery.service.impl.WeatherServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class WeatherServiceTests {
    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Before
    public void setup() {
        System.out.println("Setting up WeatherServiceTests");
        var place = placeRepository.save(new Place(69,"Test Place", "Test Country", ZoneOffset.UTC));
        System.out.println("Place created: " + place);
    }

    @Test
    public void testGenerateSampleData() {
        int n = 1;
        int beforeCount = measurementRepository.findAll().size();
        //weatherService.generateSampleData(n, 3071961);
        int afterCount = measurementRepository.findAll().size();

        assertEquals("Sample data generation failed", beforeCount + n, afterCount);
    }
}
