package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentService {

    Collection<Accident> getAllAccidents();

    void addAccident(Accident accident);

    void editAccident(Accident accident);

    Accident getAccident(int id);

}
