package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;

@AllArgsConstructor
public class AccidentTypeServiceImpl implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeRepository;

    @Override
    public AccidentType getAccidentType(int id) {
        return accidentTypeRepository.getAccidentType(id);
    }

    @Override
    public void addAccidentType(AccidentType accidentType) {
        accidentTypeRepository.addAccidentType(accidentType);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return accidentTypeRepository.getAllAccidentTypes();
    }
}
