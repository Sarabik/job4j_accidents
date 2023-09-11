package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    Collection<Accident> getAllAccidents();

    void addAccident(Accident accident);

    Accident addRulesAndAccidentType(Accident accident, String[] ruleIds);

    boolean editAccident(Accident accident);

    Optional<Accident> getAccident(int id);

}
