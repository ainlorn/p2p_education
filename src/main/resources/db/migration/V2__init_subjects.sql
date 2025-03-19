CREATE TABLE subjects
(
    id   UUID NOT NULL,
    name TEXT NOT NULL,
    CONSTRAINT pk_subjects PRIMARY KEY (id)
);

CREATE TABLE subject_topics
(
    id         UUID NOT NULL,
    subject_id UUID NOT NULL REFERENCES subjects (id) ON DELETE CASCADE,
    name       TEXT NOT NULL,
    CONSTRAINT pk_subject_topics PRIMARY KEY (id)
);

INSERT INTO subjects(id, name)
VALUES ('cba95949-8589-4e6d-a3c0-80f5f3622cfa', 'Математика - 1 семестр'),
       ('67abffc5-7d3d-4a20-ae81-d7078efec6d3', 'Дополнительные главы математики'),
       ('7a355e22-b425-444e-9689-e91caacda85d', 'Векторный анализ'),
       ('88d01f03-d71d-40b6-a5d5-937ef4926b59', 'Теория вероятностей и математическая статистика'),
       ('045f27f7-e7b2-48c2-aa98-fffd97d13aee', 'Дискретная математика и математическая логика');

INSERT INTO subject_topics(id, subject_id, name)
VALUES ('3d496fb5-bb04-4c0f-87e0-457a1584eb89', 'cba95949-8589-4e6d-a3c0-80f5f3622cfa', 'Тема 1'),
       ('eaa4b72e-0876-4f37-aebc-a31f09210d20', 'cba95949-8589-4e6d-a3c0-80f5f3622cfa', 'Тема 2'),
       ('3d955951-a77b-434f-a529-857ddd7c9b4e', 'cba95949-8589-4e6d-a3c0-80f5f3622cfa', 'Тема 3'),
       ('28a43419-a61a-4328-80ff-784ecbea7f87', '67abffc5-7d3d-4a20-ae81-d7078efec6d3', 'Тема 1'),
       ('cad09b47-38b3-46c9-b555-d47b43947689', '67abffc5-7d3d-4a20-ae81-d7078efec6d3', 'Тема 2'),
       ('3b729ecd-7320-4ac0-be92-3cb7561939f5', '67abffc5-7d3d-4a20-ae81-d7078efec6d3', 'Тема 3');
