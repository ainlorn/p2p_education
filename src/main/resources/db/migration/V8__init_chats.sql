CREATE TABLE chats
(
    id        UUID                     NOT NULL,
    type      TEXT                     NOT NULL,
    create_dt TIMESTAMP with time zone NOT NULL,
    CONSTRAINT pk_chats PRIMARY KEY (id)
);

CREATE TABLE chat_messages
(
    id        UUID                                         NOT NULL,
    chat_id   UUID REFERENCES chats (id) ON DELETE CASCADE NOT NULL,
    sender_id UUID REFERENCES users (id),
    content   JSONB                                        NOT NULL,
    create_dt TIMESTAMP with time zone                     NOT NULL,
    CONSTRAINT pk_chat_messages PRIMARY KEY (id)
);

CREATE TABLE chat_participants
(
    id                   UUID                                         NOT NULL,
    chat_id              UUID REFERENCES chats (id) ON DELETE CASCADE NOT NULL,
    user_id              UUID REFERENCES users (id)                   NOT NULL,
    last_read_message_id UUID REFERENCES chat_messages (id),
    CONSTRAINT pk_chat_participants PRIMARY KEY (id)
);

ALTER TABLE chat_participants
    ADD CONSTRAINT uc_chatparticipant_chat_id UNIQUE (chat_id, user_id);

CREATE INDEX idx_chatmessage_chat_id ON chat_messages (chat_id);

CREATE INDEX idx_chatmessage_create_dt ON chat_messages (create_dt);

CREATE INDEX idx_chatparticipant_chat_id ON chat_participants (chat_id);

CREATE INDEX idx_chatparticipant_user_id ON chat_participants (user_id);

ALTER TABLE advert_responses
    ADD chat_id UUID REFERENCES chats (id) ON DELETE SET NULL;
