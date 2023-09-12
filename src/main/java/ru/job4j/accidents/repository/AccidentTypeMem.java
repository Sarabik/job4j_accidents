package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class AccidentTypeMem implements AccidentTypeRepository {

    private static AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, AccidentType> accidentTypes = new ConcurrentHashMap<>();

    public AccidentTypeMem() {
        addAccidentType(new AccidentType(1, "Two cars"));
        addAccidentType(new AccidentType(2, "Car and person"));
        addAccidentType(new AccidentType(3, "Car and bicycle"));
    }

    @Override
    public AccidentType getAccidentType(int id) {
        return accidentTypes.get(id);
    }

    @Override
    public void addAccidentType(AccidentType accidentType) {
        if (accidentType.getId() == 0) {
            int id = counter.incrementAndGet();
            accidentType.setId(id);
        }
        accidentTypes.put(accidentType.getId(), accidentType);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return accidentTypes.values();
    }
}
