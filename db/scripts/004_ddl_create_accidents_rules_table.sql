CREATE TABLE accidents_rules (
    id serial PRIMARY KEY,
    accidents_id INT REFERENCES accidents(id),
    rules_id INT REFERENCES rules(id),
    UNIQUE (accidents_id, rules_id)
    );