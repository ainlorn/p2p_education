ALTER TABLE meetings ALTER COLUMN chat_id DROP NOT NULL;

CREATE TABLE group_meetings
(
    id          UUID                          NOT NULL,
    creator_id  UUID REFERENCES users (id)    NOT NULL,
    meeting_id  UUID REFERENCES meetings (id) NOT NULL,
    title       TEXT                          NOT NULL,
    description TEXT                          NOT NULL,
    start_dt    TIMESTAMP with time zone      NOT NULL,
    end_dt      TIMESTAMP with time zone      NOT NULL,
    create_dt   TIMESTAMP with time zone      NOT NULL,
    CONSTRAINT pk_group_meetings PRIMARY KEY (id)
);
