package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentRepository {

    Collection<Accident> getAllAccidents();

    Accident addAccident(Accident accident);

    boolean editAccident(Accident accident);

    Optional<Accident> getAccident(int id);

}
