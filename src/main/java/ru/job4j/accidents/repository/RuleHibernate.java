package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@AllArgsConstructor
public class RuleHibernate implements RuleRepository {

    private final CrudRepository crudRepository;

    @Override
    public Rule getRule(int id) {
        return crudRepository.getOptional("FROM Rule WHERE id = :id",
                Rule.class, Map.of("id", id)).get();
    }

    @Override
    public void addRule(Rule rule) {
        crudRepository.getOptional(rule);
    }

    @Override
    public Collection<Rule> getAllRules() {
        return crudRepository.query("FROM Rule", Rule.class);
    }

    @Override
    public Set<Rule> getRulesByIds(String[] ids) {
        return new HashSet<Rule>(crudRepository.query("FROM Rule WHERE id IN :ids",
                Rule.class, Map.of("ids", Arrays.stream(ids).map(Integer::parseInt).toList())));
    }
}
