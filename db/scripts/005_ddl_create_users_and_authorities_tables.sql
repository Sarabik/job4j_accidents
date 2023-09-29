CREATE TABLE authorities (
  id serial PRIMARY KEY,
  authority VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
  id serial PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(100) NOT NULL,
  enabled boolean DEFAULT TRUE,
  authority_id INT NOT NULL REFERENCES authorities(id)
);
