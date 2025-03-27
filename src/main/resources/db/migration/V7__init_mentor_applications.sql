CREATE TABLE mentor_applications
(
    id          UUID                       NOT NULL,
    student_id  UUID REFERENCES users (id) NOT NULL,
    description TEXT                       NOT NULL,
    state       TEXT                       NOT NULL,
    create_dt   TIMESTAMP with time zone   NOT NULL,
    update_dt   TIMESTAMP with time zone   NOT NULL,
    CONSTRAINT pk_mentor_applications PRIMARY KEY (id)
);

ALTER TABLE users
    ADD is_mentor BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE users
    ALTER COLUMN is_mentor DROP DEFAULT;

