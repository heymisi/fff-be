package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.SportType;

import java.util.List;

public interface SportTypeService {
    SportType addSportType(SportType sportType) throws FitforfunException;

    List<SportType> listSports();
}
