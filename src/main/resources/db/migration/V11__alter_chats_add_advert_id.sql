ALTER TABLE chats
    ADD advert_id UUID REFERENCES adverts (id) NOT NULL;

ALTER TABLE chats
    ADD advert_response_id UUID REFERENCES advert_responses (id);
