CREATE TABLE reviews
(
    id          UUID                         NOT NULL,
    reviewer_id UUID REFERENCES users (id)   NOT NULL,
    reviewee_id UUID REFERENCES users (id)   NOT NULL,
    advert_id   UUID REFERENCES adverts (id) NOT NULL,
    rating      INTEGER                      NOT NULL,
    type        TEXT                         NOT NULL,
    text        TEXT                         NOT NULL,
    create_dt   TIMESTAMP with time zone     NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);
