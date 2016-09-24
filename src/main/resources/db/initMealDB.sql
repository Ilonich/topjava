DROP TABLE IF EXISTS meals;
DROP SEQUENCE IF EXISTS meal_seq;

CREATE SEQUENCE meal_seq START 100000;

CREATE TABLE meals
(
  id INTEGER PRIMARY KEY DEFAULT nextval('meal_seq'),
  user_id INTEGER NOT NULL,
  date_time TIMESTAMP NOT NULL DEFAULT now(),
  description TEXT,
  calories INTEGER NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX meals_pkey ON meals (id);
CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON meals (user_id, date_time);
