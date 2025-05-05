package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    record PlaceTemperatureDifference(
            Integer placeId,
            Double largestTempDiff
    ){}

    @Query(value = """
                SELECT
                    place_id AS placeId,
                    MAX(max_temperature - min_temperature) AS largestTempDiff
                FROM measurement
                WHERE timestamp::date = :date
                GROUP BY place_id
                ORDER BY largestTempDiff DESC
            """, nativeQuery = true)
    List<PlaceTemperatureDifference> findTempDiffsByDate(@Param("date") LocalDate date);
}
