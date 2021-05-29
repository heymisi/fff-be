package hu.fitforfun.controller;

import hu.fitforfun.model.SportType;
import hu.fitforfun.repositories.SportTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sports")
public class SportTypeController {
    @Autowired
    SportTypeRepository sportTypeRepository;

    @GetMapping("")
    @Cacheable("sports")
    public List<SportType> getSports() {
        List<SportType> returnSports = new ArrayList<>();
        sportTypeRepository.findAll().iterator().forEachRemaining(returnSports::add);
        return returnSports;
    }

    @GetMapping("/{id}")
    public SportType getSportTypeById(@PathVariable Long id) {
        return sportTypeRepository.findById(id).get();
    }

    @GetMapping("/byName/{name}")
    public SportType getSportTypeByName(@PathVariable String name) {
        return sportTypeRepository.findByNameIgnoreCase(name).get();
    }
}
