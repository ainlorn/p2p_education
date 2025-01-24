CREATE TABLE users
(
    id          UUID                     NOT NULL,
    username    TEXT                     NOT NULL,
    email       TEXT                     NOT NULL,
    first_name  TEXT,
    last_name   TEXT,
    middle_name TEXT,
    university  TEXT,
    faculty     TEXT,
    course      INTEGER,
    role        TEXT                     NOT NULL,
    description TEXT                     NOT NULL,
    create_dt   TIMESTAMP with time zone NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
