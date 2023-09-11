package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;

    private final RuleService ruleService;

    private final AccidentTypeService accidentTypeService;

    @Override
    public Collection<Accident> getAllAccidents() {
        return accidentRepository.getAllAccidents();
    }

    @Override
    public void addAccident(Accident accident) {
        accidentRepository.addAccident(accident);
    }

    @Override
    public Accident addRulesAndAccidentType(Accident accident, String[] ruleIds) {
        Set<Rule> rules = new HashSet<>();
        for (String id : ruleIds) {
            rules.add(ruleService.getRule(Integer.parseInt(id)));
        }
        accident.setRules(rules);
        int typeId = accident.getType().getId();
        accident.setType(accidentTypeService.getAccidentType(typeId));
        return accident;
    }

    @Override
    public boolean editAccident(Accident accident) {
        return accidentRepository.editAccident(accident);
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        return accidentRepository.getAccident(id);
    }
}
