package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidentRepository.getAllAccidents();
    }

    @Override
    public void addAccident(Accident accident) {
        accidentRepository.addAccident(accident);
    }
}
