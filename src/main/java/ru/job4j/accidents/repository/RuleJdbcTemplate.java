package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public Rule getRule(int id) {
        return jdbc.queryForObject("SELECT * from rules WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> {
                    Rule rule = new Rule();
                    rule.setName(rs.getString("name"));
                    rule.setId(rs.getInt("id"));
                    return rule;
                });
    }

    @Override
    public void addRule(Rule rule) {
        jdbc.update("INSERT INTO rules (name) VALUES (?)",
                rule.getName());
    }

    @Override
    public Collection<Rule> getAllRules() {
        return jdbc.query("SELECT * FROM rules",
                (rs, row) -> {
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("id"));
                    rule.setName(rs.getString("name"));
                    return rule;
                });
    }
}
