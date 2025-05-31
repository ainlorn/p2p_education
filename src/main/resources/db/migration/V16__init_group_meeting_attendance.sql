CREATE TABLE group_meeting_attendances
(
    id               UUID                                NOT NULL,
    group_meeting_id UUID REFERENCES group_meetings (id) NOT NULL,
    user_id          UUID REFERENCES users (id)          NOT NULL,
    create_dt        TIMESTAMP with time zone            NOT NULL,
    CONSTRAINT pk_group_meeting_attendances PRIMARY KEY (id)
);

ALTER TABLE group_meeting_attendances
    ADD CONSTRAINT uc_groupmeetingattendance UNIQUE (group_meeting_id, user_id);
