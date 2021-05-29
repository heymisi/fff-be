package hu.fitforfun.repositories;

import hu.fitforfun.model.address.City;
import hu.fitforfun.model.address.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByCounty(County county);

    City findByCityNameIgnoreCase(String cityName);
}
