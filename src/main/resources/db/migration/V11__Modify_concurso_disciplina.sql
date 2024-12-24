ALTER TABLE contest_disciplina
ADD COLUMN ciclos INT NOT NULL DEFAULT 0;

ALTER TABLE contest_disciplina
ADD COLUMN ordem NUMERIC NULL;