package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidentRepository.getAllAccidents();
    }

    @Override
    public void addAccident(Accident accident) {
        accidentRepository.addAccident(accident);
    }

    @Override
    public void editAccident(Accident accident) {
        accidentRepository.editAccident(accident);
    }

    @Override
    public Accident getAccident(int id) {
        return accidentRepository.getAccident(id);
    }
}
