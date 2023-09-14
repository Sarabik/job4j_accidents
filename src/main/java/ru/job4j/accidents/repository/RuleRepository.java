package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Set;

public interface RuleRepository {

    Rule getRule(int id);

    void addRule(Rule rule);

    Collection<Rule> getAllRules();

    Set<Rule> getRulesByIds(String[] ids);
}
