package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.dto.MeasurementAverages;
import cz.pycrs.cloudquery.entity.Measurement;
import cz.pycrs.cloudquery.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Page<Measurement> findAllByPlace(Place place, Pageable pageable);

    @Query("""
            SELECT
                new cz.pycrs.cloudquery.dto.MeasurementAverages(
                    AVG(m.temperature),
                    AVG(m.feelsLike),
                    AVG(m.minTemperature),
                    AVG(m.maxTemperature),
                    AVG(m.pressureGroundLevel),
                    AVG(m.pressureSeaLevel),
                    AVG(m.humidity)
                )
            FROM Measurement m
            WHERE m.place = :place
            """)
    MeasurementAverages findAveragesByPlace(@Param("place") Place place);
}
