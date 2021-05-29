package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.SportType;
import hu.fitforfun.repositories.SportTypeRepository;
import hu.fitforfun.services.SportTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class SportTypeServiceImpl implements SportTypeService {
    @Autowired
    SportTypeRepository sportTypeRepository;

    @Override
    public SportType addSportType(SportType sportType) throws FitforfunException {
        if (sportTypeRepository.findByNameIgnoreCase(sportType.getName()).isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_TYPE_ALREADY_EXISTS);
        }
        sportTypeRepository.save(sportType);
        return sportType;
    }

    @Override
    public List<SportType> listSports() {
        List<SportType> returnValue = new ArrayList<>();
        sportTypeRepository.findAll().forEach(returnValue::add);
        return returnValue;
    }
}
