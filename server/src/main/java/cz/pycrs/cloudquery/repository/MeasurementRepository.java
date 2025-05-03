package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, String> {
    List<Measurement> findAllByRainIntensity(double rainIntensity);
}
