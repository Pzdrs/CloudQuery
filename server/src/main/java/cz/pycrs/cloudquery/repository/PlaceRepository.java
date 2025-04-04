package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Optional<Place> findByCityName(String cityName);
}
