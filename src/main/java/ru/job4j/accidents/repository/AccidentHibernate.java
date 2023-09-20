package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Accident> getAllAccidents() {
        return crudRepository.query("FROM Accident ORDER BY id", Accident.class);
    }

    @Override
    public Optional<Accident> addAccident(Accident accident) {
        return crudRepository.getOptional(accident);
    }

    @Override
    public boolean editAccident(Accident accident) {
        return crudRepository.ifChanged(accident);
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return crudRepository.getOptional("FROM Accident a JOIN FETCH a.rules WHERE a.id = :id",
                                            Accident.class, Map.of("id", id));
    }
}
