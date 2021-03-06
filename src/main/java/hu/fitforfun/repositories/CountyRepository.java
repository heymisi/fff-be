package hu.fitforfun.repositories;

import hu.fitforfun.model.address.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {
    County findByCountyName(String countyName);
}
