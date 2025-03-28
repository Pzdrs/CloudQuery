package cz.pycrs.cloudquery.repository;

import cz.pycrs.cloudquery.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
