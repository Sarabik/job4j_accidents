package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleDataRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RuleDataService implements RuleService {

    private final RuleDataRepository ruleDataRepository;

    @Override
    public Rule getRule(int id) {
        return ruleDataRepository.findById(id).get();
    }

    @Override
    public void addRule(Rule rule) {
        ruleDataRepository.save(rule);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return ruleDataRepository.findAll();
    }
}
