package ru.job4j.accidents.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.accidents.model.Rule;

import java.util.*;

public interface RuleDataRepository extends CrudRepository<Rule, Integer> {

    @Override
    Collection<Rule> findAll();

    @Query("FROM Rule WHERE id IN :ids")
    Set<Rule> getRulesByIds(@Param("ids") List<Integer> ids);

}
