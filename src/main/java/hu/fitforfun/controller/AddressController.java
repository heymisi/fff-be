package hu.fitforfun.controller;

import hu.fitforfun.model.address.City;
import hu.fitforfun.model.address.County;
import hu.fitforfun.repositories.CityRepository;
import hu.fitforfun.repositories.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    CityRepository cityController;

    @Autowired
    CountyRepository countyController;

    @GetMapping("/cities")
    @Cacheable("cities")
    public List<City> getAllCities() {
        return cityController.findAll();
    }


    @GetMapping("/cities/{county}")
    @Cacheable("citiesByCounty")
    public List<City> getAllCitiesByCounty(@PathVariable String county) {

        return cityController.findByCounty(countyController.findByCountyName(county));
    }

    @GetMapping("/counties")
    @Cacheable("counties")
    public List<County> getAllCounties() {
        return countyController.findAll();
    }

}
