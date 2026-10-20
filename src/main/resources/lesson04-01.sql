DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         bigserial,
    login      varchar(50) NOT NULL,
    password   varchar(55),
    role       varchar(10),
    game_scope integer
);

INSERT INTO users (login, password, role, game_scope)
VALUES ('Carl', '456', 'ADMIN', 100),
       ('Alisa', '123', 'USER', 77),
       ('Bob', '789', 'GUEST', 12),
       ('Elon', 'Musk', 'ADMIN', 10),
       ('Mike', 'T', 'GUEST', 7),
       ('Michael', 'Jackson', 'USER', 22)
;

SELECT *
  FROM users
 WHERE id < 40
 ORDER BY id DESC;

UPDATE public.users
   SET password = 'Touch1'
 WHERE id = 5;

DELETE
  FROM users
 WHERE id IN (4, 5, 6);
