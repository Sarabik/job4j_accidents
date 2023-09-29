INSERT INTO authorities (authority) VALUES ('ROLE_USER');
INSERT INTO authorities (authority) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, enabled, password, authority_id)
VALUES ('admin', true, '$2y$10$JxMT7QoGgkjsb1YN2Y1Dh.CrONtHZKZ96wwNMoB9KILx/FDdey14.',
(select id from authorities where authority = 'ROLE_ADMIN'));

INSERT INTO users (username, enabled, password, authority_id)
VALUES ('user', true, '$2y$10$9mYXFacGyRZadVsPef5vAODjcoJq9JX6MZ370aSJl9KhKV3cT5.Qu',
(select id from authorities where authority = 'ROLE_USER'));