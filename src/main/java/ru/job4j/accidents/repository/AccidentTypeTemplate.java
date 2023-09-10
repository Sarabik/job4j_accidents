package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class AccidentTypeTemplate implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public AccidentType getAccidentType(int id) {
        return jdbc.queryForObject("SELECT * from accident_types WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setName(rs.getString("name"));
                    accidentType.setId(rs.getInt("id"));
                    return accidentType;
                });
    }

    @Override
    public void addAccidentType(AccidentType accidentType) {
        jdbc.update("INSERT INTO accident_types (name) VALUES (?)",
                accidentType.getName());
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        return jdbc.query("SELECT * FROM accident_types",
                (rs, row) -> {
                    AccidentType accidentType = new AccidentType();
                    accidentType.setId(rs.getInt("id"));
                    accidentType.setName(rs.getString("name"));
                    return accidentType;
                });
    }
}
