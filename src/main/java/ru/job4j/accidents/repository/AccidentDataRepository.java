package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;

public interface AccidentDataRepository extends CrudRepository<Accident, Integer> {

    @Override
    Collection<Accident> findAll();
}
