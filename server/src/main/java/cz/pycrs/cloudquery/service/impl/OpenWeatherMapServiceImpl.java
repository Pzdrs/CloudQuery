package cz.pycrs.cloudquery.service.impl;

import cz.pycrs.cloudquery.repository.OWMResponseRepository;
import cz.pycrs.cloudquery.service.OpenWeatherMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {
    private final OWMResponseRepository responseRepository;
}
