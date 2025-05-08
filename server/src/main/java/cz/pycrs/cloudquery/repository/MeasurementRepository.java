package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.dto.AverageWeather;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, String> {
    List<Measurement> findAllByPlace(Place place);

    AverageWeather findAverage(@Param() int placeId, @Param() int days);
}
