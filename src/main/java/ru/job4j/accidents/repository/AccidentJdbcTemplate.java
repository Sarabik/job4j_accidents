package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private final AccidentTypeRepository accidentTypeRepository;

    private final RuleRepository ruleRepository;

    @Override
    public Accident addAccident(Accident accident) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", accident.getName());
        parameters.put("text", accident.getText());
        parameters.put("address", accident.getAddress());
        parameters.put("accident_types_id", accident.getType().getId());
        int id = (int) new SimpleJdbcInsert(jdbc)
                .withTableName("accidents")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(parameters);
        accident.setId(id);
        Set<Rule> rules = accident.getRules();
        for (Rule rule : rules) {
            jdbc.update("""
                    INSERT INTO accidents_rules (accidents_id, rules_id)
                    VALUES (?, ?)""",
                    id,
                    rule.getId());
        }
        return accident;
    }

    @Override
    public Collection<Accident> getAllAccidents() {
        return jdbc.query("SELECT * FROM accidents ORDER BY id",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    int typeId = rs.getInt("accident_types_id");
                    AccidentType accidentType = accidentTypeRepository.getAccidentType(typeId);
                    accident.setType(accidentType);

                    Set<Rule> rules = new HashSet<>(
                            jdbc.query("SELECT rules_id FROM accidents_rules WHERE accidents_id = ?",
                            new Object[]{accident.getId()},
                            (rs1, row1) -> ruleRepository.getRule(rs1.getInt("rules_id"))));
                    accident.setRules(rules);
                    return accident;
                });
    }

    @Override
    public void editAccident(Accident accident) {
        jdbc.update("""
                UPDATE accidents SET name = ?, text = ?, address = ?, accident_types_id = ?
                WHERE id = ?""",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        jdbc.update("DELETE FROM accidents_rules WHERE id = ?", accident.getId());
        Set<Rule> rules = accident.getRules();
        for (Rule rule : rules) {
            jdbc.update("""
                    INSERT INTO accidents_rules (accidents_id, rules_id)
                    VALUES (?, ?)""",
                    accident.getId(),
                    rule.getId());
        }
    }

    @Override
    public Accident getAccident(int id) {
        return jdbc.queryForObject("SELECT * from accidents WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    accident.setText(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    int typeId = rs.getInt("accident_types_id");
                    AccidentType accidentType = accidentTypeRepository.getAccidentType(typeId);
                    accident.setType(accidentType);

                    Set<Rule> rules = new HashSet<>(
                            jdbc.query("SELECT rules_id FROM accidents_rules WHERE accidents_id = ?",
                                    new Object[]{accident.getId()},
                                    (rs1, row1) -> ruleRepository.getRule(rs1.getInt("rules_id"))));
                    accident.setRules(rules);
                    return accident;
                });
    }

}