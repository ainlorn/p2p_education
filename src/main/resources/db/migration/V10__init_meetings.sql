CREATE TABLE meetings
(
    id        UUID                                         NOT NULL,
    chat_id   UUID REFERENCES chats (id) ON DELETE CASCADE NOT NULL,
    name      TEXT                                         NOT NULL,
    create_dt TIMESTAMP with time zone                     NOT NULL,
    CONSTRAINT pk_meetings PRIMARY KEY (id)
);
