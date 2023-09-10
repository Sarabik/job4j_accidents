package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class RuleServiceImpl implements RuleService {

    private final RuleRepository ruleRepository;

    @Override
    public Rule getRule(int id) {
        return ruleRepository.getRule(id);
    }

    @Override
    public void addRule(Rule rule) {
        ruleRepository.addRule(rule);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return ruleRepository.getAllRules();
    }
}
