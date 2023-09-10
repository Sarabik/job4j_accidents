package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface RuleService {

    Rule getRule(int id);

    void addRule(Rule rule);

    Collection<Rule> getAllRules();
}
