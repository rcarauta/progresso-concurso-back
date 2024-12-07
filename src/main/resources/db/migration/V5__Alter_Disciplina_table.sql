ALTER TABLE disciplina
ADD COLUMN categoria TEXT CHECK (categoria IN ('BASICA', 'ESPECIFICA'));