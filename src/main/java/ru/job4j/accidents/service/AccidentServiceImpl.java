package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final RuleRepository ruleRepository;

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidentRepository.getAllAccidents();
    }

    @Override
    public void addAccident(Accident accident) {
        accidentRepository.addAccident(accident);
    }

    @Override
    public void addAccident(Accident accident, String[] ruleIds) {
        accident.setRules(ruleRepository.getRulesByIds(ruleIds));
        addAccident(accident);
    }

    @Override
    public boolean editAccident(Accident accident) {
        return accidentRepository.editAccident(accident);
    }

    @Override
    public boolean editAccident(Accident accident, String[] ruleIds) {
        accident.setRules(ruleRepository.getRulesByIds(ruleIds));
        return editAccident(accident);
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return accidentRepository.getAccident(id);
    }
}
