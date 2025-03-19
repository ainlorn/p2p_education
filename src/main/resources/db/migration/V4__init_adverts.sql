CREATE TABLE adverts
(
    id          UUID                          NOT NULL,
    mentor_id   UUID REFERENCES users (id),
    student_id  UUID REFERENCES users (id),
    title       TEXT                          NOT NULL,
    description TEXT                          NOT NULL,
    subject_id  UUID REFERENCES subjects (id) NOT NULL,
    status      TEXT                          NOT NULL,
    type        TEXT                          NOT NULL,
    create_dt   TIMESTAMP with time zone      NOT NULL,
    update_dt   TIMESTAMP with time zone      NOT NULL,
    tsv         TSVECTOR,
    CONSTRAINT pk_adverts PRIMARY KEY (id)
);

CREATE TABLE advert_topics
(
    id        UUID                                                  NOT NULL,
    advert_id UUID REFERENCES adverts (id) ON DELETE CASCADE        NOT NULL,
    topic_id  UUID REFERENCES subject_topics (id) ON DELETE CASCADE NOT NULL,
    CONSTRAINT pk_advert_topics PRIMARY KEY (id)
);

ALTER TABLE advert_topics
    ADD CONSTRAINT uc_adverttopic_advert_id_topic_id UNIQUE (advert_id, topic_id);

CREATE INDEX idx_adverttopic_advert_id ON advert_topics (advert_id);

CREATE FUNCTION update_adverts_tsv() RETURNS trigger AS $$
DECLARE
    row record;
    cfg regconfig;
BEGIN
    cfg := (tg_argv[0])::regconfig;
    new.tsv :=
            to_tsvector(cfg, coalesce(new.title,'')) ||
            to_tsvector(cfg, coalesce(new.description,''));
    
    for row in
        select * from users u
        where u.id=new.mentor_id or u.id=new.student_id
        loop
            new.tsv := new.tsv ||
                       to_tsvector(cfg, coalesce(row.first_name, '')) ||
                       to_tsvector(cfg, coalesce(row.last_name, '')) ||
                       to_tsvector(cfg, coalesce(row.middle_name, ''));
        end loop;

    for row in
        select * from subjects s
        where s.id=new.subject_id
        loop
            new.tsv := new.tsv || to_tsvector(cfg, coalesce(row."name", ''));
        end loop;

    for row in
        select st.* from advert_topics at
                             join subject_topics st on at.topic_id = st.id
        where at.advert_id=new.id
        loop
            new.tsv := new.tsv || to_tsvector(cfg, coalesce(row."name", ''));
        end loop;

    return new;
END
$$ LANGUAGE plpgsql;


CREATE TRIGGER advert_tsvector_update BEFORE INSERT OR UPDATE
    ON adverts FOR EACH ROW EXECUTE FUNCTION
    update_adverts_tsv('ts_cfg');

CREATE INDEX adverts_tsv_idx ON adverts USING GIN(tsv);
