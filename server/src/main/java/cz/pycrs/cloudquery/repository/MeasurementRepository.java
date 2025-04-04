package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, String> {
}
