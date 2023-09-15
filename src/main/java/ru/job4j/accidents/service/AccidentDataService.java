package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentDataRepository;
import ru.job4j.accidents.repository.RuleDataRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentDataService implements AccidentService {

    private final AccidentDataRepository accidentDataRepository;

    private final RuleDataRepository ruleDataRepository;

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidentDataRepository.findAll();
    }

    @Override
    public void addAccident(Accident accident) {
        accidentDataRepository.save(accident);
    }

    @Override
    public void addAccident(Accident accident, String[] ruleIds) {
        List<Integer> ids = Arrays.stream(ruleIds).map(Integer::parseInt).toList();
        accident.setRules(ruleDataRepository.getRulesByIds(ids));
        addAccident(accident);
    }

    @Override
    public boolean editAccident(Accident accident) {
        accidentDataRepository.save(accident);
        return true;
    }

    @Override
    public boolean editAccident(Accident accident, String[] ruleIds) {
        List<Integer> ids = Arrays.stream(ruleIds).map(Integer::parseInt).toList();
        accident.setRules(ruleDataRepository.getRulesByIds(ids));
        return editAccident(accident);
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return accidentDataRepository.findById(id);
    }
}
