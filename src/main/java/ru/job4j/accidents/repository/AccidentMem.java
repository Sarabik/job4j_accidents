package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
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
    public Accident addAccident(Accident accident) {
        int id = counter.incrementAndGet();
        accident.setId(id);
        accidents.put(id, accident);
        return accident;
    }

    @Override
    public void editAccident(Accident accident) {
        accidents.put(accident.getId(), accident);
    }

    @Override
    public Accident getAccident(int id) {
        return accidents.get(id);
    }
}
