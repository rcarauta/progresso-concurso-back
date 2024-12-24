-- Migration 10: Alterar tabela 'materia' e criar colunas em 'contest_disciplina_materia'

-- Remover colunas da tabela 'materia'
ALTER TABLE materia
DROP COLUMN porcentagem,
DROP COLUMN tempo_estudo,
DROP COLUMN total_questoes,
DROP COLUMN questoes_acertadas;

-- Adicionar colunas na tabela 'contest_disciplina_materia'
ALTER TABLE contest_disciplina_materia
ADD COLUMN porcentagem NUMERIC DEFAULT 0,
ADD COLUMN tempo_estudo INTERVAL DEFAULT '0',
ADD COLUMN total_questoes NUMERIC DEFAULT 0,
ADD COLUMN questoes_acertadas NUMERIC DEFAULT 0;