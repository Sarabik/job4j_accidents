package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    Collection<Accident> getAllAccidents();

    void addAccident(Accident accident);

    void addAccident(Accident accident, String[] ruleIds);

    boolean editAccident(Accident accident);

    boolean editAccident(Accident accident, String[] ruleIds);

    Optional<Accident> getAccident(int id);

}
