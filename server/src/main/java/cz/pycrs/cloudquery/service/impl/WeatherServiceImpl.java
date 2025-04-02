package cz.pycrs.cloudquery.service.impl;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import cz.pycrs.cloudquery.repository.OWMResponseRepository;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private static final Logger logger = Logger.getLogger(WeatherServiceImpl.class.getName());
    private final OpenWeatherMapClient owmClient;
    private final OWMResponseRepository responseRepository;

    @Override
    public void generateSampleData(int n) {
        logger.info("Generating " + n + " sample weather data records.");
    }
}
