package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeDataRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AccidentTypeDataService implements AccidentTypeService {

    AccidentTypeDataRepository accidentTypeDataRepository;

    @Override
    public AccidentType getAccidentType(int id) {
        return accidentTypeDataRepository.findById(id).get();
    }

    @Override
    public void addAccidentType(AccidentType accidentType) {
        accidentTypeDataRepository.save(accidentType);
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return accidentTypeDataRepository.findAll();
    }
}
