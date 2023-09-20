package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class AccidentTypeHibernate implements AccidentTypeRepository {

    private final CrudRepository crudRepository;

    @Override
    public AccidentType getAccidentType(int id) {
        return crudRepository.getOptional("FROM AccidentType WHERE id = :id",
                AccidentType.class, Map.of("id", id)).get();
    }

    @Override
    public void addAccidentType(AccidentType accidentType) {
        crudRepository.getOptional(accidentType);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return crudRepository.query("FROM AccidentType", AccidentType.class);
    }
}
