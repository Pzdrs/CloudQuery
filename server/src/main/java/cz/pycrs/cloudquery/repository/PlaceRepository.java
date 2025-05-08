package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
}
