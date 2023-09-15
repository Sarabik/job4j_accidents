package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

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
        return jdbc.query("""
                        SELECT  a.id        AS id,
                                a.name      AS name,
                                a.text      AS text,
                                a.address   AS address,
                                t.id        AS type_id,
                                t.name      AS type_name
                        FROM accidents AS a
                        INNER JOIN accident_types AS t
                        ON t.id = a.accident_types_id
                        ORDER BY a.id;
                        """,
                getRowMapperForAccident());
    }

    @Override
    public boolean editAccident(Accident accident) {
        boolean changed = jdbc.update("""
                UPDATE accidents SET name = ?, text = ?, address = ?, accident_types_id = ?
                WHERE id = ?""",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId()) > 0;
        jdbc.update("DELETE FROM accidents_rules WHERE accidents_id = ?", accident.getId());
        Set<Rule> rules = accident.getRules();
        for (Rule rule : rules) {
            jdbc.update("""
                    INSERT INTO accidents_rules (accidents_id, rules_id)
                    VALUES (?, ?)""",
                    accident.getId(),
                    rule.getId());
        }
        return changed;
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        Accident accident = jdbc.queryForObject("""
                        SELECT  a.id        AS id,
                                a.name      AS name,
                                a.text      AS text,
                                a.address   AS address,
                                t.id        AS type_id,
                                t.name      AS type_name
                        FROM accidents AS a
                        INNER JOIN accident_types AS t
                        ON t.id = a.accident_types_id
                        WHERE a.id = ?;
                        """,
                getRowMapperForAccident(), id);
        return Optional.ofNullable(accident);
    }

    private Set<Rule> getRuleSetByAccidentId(int accidentId) {
        return new HashSet<Rule>(
                jdbc.query("""
                                            SELECT  r.id    AS id,
                                                    r.name  AS name
                                            FROM accidents_rules AS ar
                                            INNER JOIN rules AS r
                                            ON ar.rules_id = r.id
                                            WHERE ar.accidents_id = ?;
                                            """,
                        (rs, row) -> new Rule(rs.getInt("id"), rs.getString("name")),
                        accidentId));
    }

    private RowMapper<Accident> getRowMapperForAccident() {
        return (rs, rowNum) -> {
            Accident accident = new Accident();
            accident.setId(rs.getInt("id"));
            accident.setName(rs.getString("name"));
            accident.setText(rs.getString("text"));
            accident.setAddress(rs.getString("address"));
            accident.setType(
                    new AccidentType(rs.getInt("type_id"), rs.getString("type_name"))
            );
            accident.setRules(getRuleSetByAccidentId(accident.getId()));
            return accident;
        };
    }

}