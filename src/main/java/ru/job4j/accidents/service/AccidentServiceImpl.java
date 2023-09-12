package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.AccidentTypeRepository;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final RuleRepository ruleRepository;

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
    public void addAccident(Accident accident, String[] ruleIds) {
        addAccident(addRulesAndAccidentType(accident, ruleIds));
    }

    public Accident addRulesAndAccidentType(Accident accident, String[] ruleIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(ruleRepository.getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        int typeId = accident.getType().getId();
        accident.setType(accidentTypeRepository.getAccidentType(typeId));
        return accident;
    }

    @Override
    public boolean editAccident(Accident accident) {
        return accidentRepository.editAccident(accident);
    }

    @Override
    public boolean editAccident(Accident accident, String[] ruleIds) {
        return editAccident(addRulesAndAccidentType(accident, ruleIds));
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return accidentRepository.getAccident(id);
    }
}
