package cz.pycrs.cloudquery.service.impl;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import cz.pycrs.cloudquery.repository.OWMResponseRepository;
import cz.pycrs.cloudquery.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final OpenWeatherMapClient owmClient;
    private final OWMResponseRepository responseRepository;
}
