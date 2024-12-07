-- Criação da tabela 'disciplina'
CREATE TABLE disciplina (
    id SERIAL PRIMARY KEY,  -- Usando SERIAL para auto incremento
    nome VARCHAR(255) NOT NULL,
    porcentagem FLOAT
);

-- Criação da tabela de junção 'concurso_disciplina' para relacionamento muitos para muitos
CREATE TABLE contest_disciplina (
    contest_id BIGINT NOT NULL,
    disciplina_id BIGINT NOT NULL,
    PRIMARY KEY (contest_id, disciplina_id),
    CONSTRAINT fk_contest FOREIGN KEY (contest_id) REFERENCES contest(id) ON DELETE CASCADE,
    CONSTRAINT fk_disciplina FOREIGN KEY (disciplina_id) REFERENCES disciplina(id) ON DELETE CASCADE
);