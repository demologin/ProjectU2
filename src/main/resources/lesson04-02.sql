DROP TABLE IF EXISTS players;

CREATE TABLE players
(
    id         bigserial PRIMARY KEY,
    login      varchar(50) NOT NULL UNIQUE,
    password   varchar(55),
    role       varchar(10),
    game_scope integer,
    date       DATE
);

INSERT INTO players (login, password, role, game_scope, date)
VALUES ('Carl', '456', 'ADMIN', 100, '2026-10-19'),
       ('Alisa', '123', 'USER', 77, '2026-10-21'),
       ('Bob', '789', 'GUEST', 12, '2025-10-19'),
       ('Elon', 'Musk', 'ADMIN', 10, '2026-09-01'),
       ('Mike', 'T', 'GUEST', 7, '2025-01-01'),
       ('Michael', 'Jackson', 'USER', 22, '2026-03-09'),
       ('Olga', 'Ivanova', 'ADMIN', 33, '2026-04-19'),
       ('Oleg', 'Petrov', 'USER', 11, '2026-06-12'),
       ('Jack', 'Dou', 'USER', 21, '2026-08-14')
;


SELECT id, login, role, game_scope, date registration_date
  FROM players
 WHERE id > 1
   AND role = 'ADMIN'
 ORDER BY id DESC;


SELECT login, password, role
  FROM players
 WHERE login ILIKE '%_a_%';


SELECT id, login
  FROM players
 WHERE id BETWEEN 3 AND 7;


DELETE
  FROM players
 WHERE id = 5;

SELECT id, login, role
  FROM players
 WHERE id IN (3, 4, 5, 6, 7);

SELECT *
  FROM players
 WHERE role = 'USER'
   AND date < NOW();


SELECT role, SUM(game_scope)
  FROM players
 WHERE id != 2
 GROUP BY role;

SELECT *
  FROM players
 ORDER BY game_scope;

SELECT MIN(game_scope)
  FROM players;

SELECT *
  FROM players;

SELECT id, login, game_scope
  FROM players
 WHERE game_scope = (SELECT MIN(game_scope) FROM players);

SELECT AVG(game_scope)
  FROM players;

SELECT login,
       game_scope,
       game_scope * 10 KGame,
       CASE
           WHEN game_scope > (SELECT AVG(game_scope) FROM players)
               THEN 'Winner'
           ELSE 'Loser'
           END AS      title
  FROM players;


SELECT role,
       SUM(game_scope) s,
       AVG(game_scope),
       MIN(game_scope),
       MAX(game_scope)
  FROM players
 WHERE id > 4
 GROUP BY role
HAVING SUM(game_scope) > 20
 ORDER BY s;

SELECT login, password, EXTRACT(YEAR FROM date) "ГОД"
  FROM players;

SELECT DISTINCT  role, login
  FROM players;




