CREATE TABLE advert_responses
(
    id            UUID                         NOT NULL,
    advert_id     UUID REFERENCES adverts (id) NOT NULL,
    respondent_id UUID REFERENCES users (id)   NOT NULL,
    description   TEXT                         NOT NULL,
    create_dt     TIMESTAMP with time zone     NOT NULL,
    CONSTRAINT pk_advert_responses PRIMARY KEY (id)
);

ALTER TABLE advert_responses
    ADD CONSTRAINT uc_advertresponse_advert_id_respondent_id UNIQUE (advert_id, respondent_id);

CREATE INDEX idx_advertresponse_advert_id ON advert_responses (advert_id);
