package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RuleMem implements RuleRepository {

    private static AtomicInteger counter = new AtomicInteger(0);

    private Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    @Override
    public Rule getRule(int id) {
        return rules.get(id);
    }

    @Override
    public void addRule(Rule rule) {
        if (rule.getId() == 0) {
            int id = counter.incrementAndGet();
            rule.setId(id);
        }
        rules.put(rule.getId(), rule);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return rules.values();
    }
}
