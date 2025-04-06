ALTER TABLE chat_messages
    ADD type TEXT;

ALTER TABLE chat_messages
    ALTER COLUMN type SET NOT NULL;
