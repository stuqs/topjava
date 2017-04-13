DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
  id               INT UNSIGNED NOT NULL PRIMARY KEY     AUTO_INCREMENT,
  name             VARCHAR(100) NOT NULL,
  email            VARCHAR(45)  NOT NULL,
  password         VARCHAR(45)  NOT NULL,
  registered       TIMESTAMP    NOT NULL                 DEFAULT CURRENT_TIMESTAMP,
  enabled          TINYINT      NOT NULL                 DEFAULT 1,
  calories_per_day INT          NOT NULL                 DEFAULT 2000
)
  AUTO_INCREMENT = 100000;

CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE user_roles (
  user_id INT UNSIGNED NOT NULL,
  role    VARCHAR(45),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);

CREATE TABLE meals (
  id          INT UNSIGNED NOT NULL PRIMARY KEY     AUTO_INCREMENT,
  date_time   TIMESTAMP    NOT NULL,
  description VARCHAR(255),
  calories    INT UNSIGNED,
  user_id     INT UNSIGNED NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);