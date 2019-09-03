DROP TABLE IF EXISTS cities;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE cities (
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  city        VARCHAR   NOT NULL,
  info        VARCHAR NOT NULL
);
CREATE UNIQUE INDEX city_unique
  ON cities (city);