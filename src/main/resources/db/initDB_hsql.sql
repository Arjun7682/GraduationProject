DROP TABLE user_roles IF EXISTS;
DROP TABLE voting_history IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE menus IF EXISTS;
DROP TABLE restaurants IF EXISTS;

DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name    VARCHAR(255)         NOT NULL,
    enabled BOOLEAN DEFAULT TRUE NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx
    ON restaurants (name);

CREATE TABLE menus
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER      NOT NULL,
    date_time     TIMESTAMP    NOT NULL,
    dish          VARCHAR(255) NOT NULL,
    price         INTEGER      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE voting_history
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date_time     TIMESTAMP DEFAULT now() NOT NULL,
    user_id       INTEGER                 NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX voting_history_unique_user_datetime_idx
    ON voting_history (user_id, date_time);
