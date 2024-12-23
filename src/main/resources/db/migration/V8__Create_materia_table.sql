-- Tabela materia
CREATE TABLE materia (
    id SERIAL PRIMARY KEY,               -- Auto incremento
    nome VARCHAR(255) NOT NULL,          -- Nome da matéria
    porcentagem NUMERIC(5, 2),           -- Porcentagem associada à matéria (mais preciso que FLOAT)
    tempo_estudo INTERVAL                -- Tempo de estudo para a matéria (INTERVAL é usado no PostgreSQL)
);

-- Tabela intermediária para relacionar matéria, disciplina e concurso
CREATE TABLE contest_disciplina_materia (
    materia_id INT NOT NULL,             -- Chave estrangeira para matéria
    disciplina_id INT NOT NULL,          -- Chave estrangeira para disciplina
    concurso_id INT NOT NULL,            -- Chave estrangeira para concurso
    PRIMARY KEY (materia_id, disciplina_id, concurso_id), -- Chave composta
    FOREIGN KEY (materia_id) REFERENCES materia(id) ON DELETE CASCADE,
    FOREIGN KEY (disciplina_id) REFERENCES disciplina(id) ON DELETE CASCADE,
    FOREIGN KEY (concurso_id) REFERENCES contest(id) ON DELETE CASCADE
);
