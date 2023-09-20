package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AccidentMem implements AccidentRepository {

    private static AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidents.values();
    }

    @Override
    public Optional<Accident> addAccident(Accident accident) {
        int id = counter.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return Optional.ofNullable(accident);
    }

    @Override
    public boolean editAccident(Accident accident) {
        return accidents.put(accident.getId(), accident) != null;
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return Optional.of(accidents.get(id));
    }

}
