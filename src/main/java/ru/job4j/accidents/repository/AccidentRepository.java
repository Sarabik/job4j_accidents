package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentRepository {

    Collection<Accident> getAllAccidents();

    Accident addAccident(Accident accident);

    void editAccident(Accident accident);

    Accident getAccident(int id);

}
