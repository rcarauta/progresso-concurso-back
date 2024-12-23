package br.co.progresso.concurso.infra.concurso_disciplina_materia;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConcursoDisciplinaMateriaId implements Serializable {
    private Long materiaId;
    private Long disciplinaId;
    private Long concursoId;

    public ConcursoDisciplinaMateriaId() {}

    public ConcursoDisciplinaMateriaId(Long materiaId, Long disciplinaId, Long concursoId) {
        this.materiaId = materiaId;
        this.disciplinaId = disciplinaId;
        this.concursoId = concursoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcursoDisciplinaMateriaId that = (ConcursoDisciplinaMateriaId) o;
        return Objects.equals(materiaId, that.materiaId) &&
               Objects.equals(disciplinaId, that.disciplinaId) &&
               Objects.equals(concursoId, that.concursoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materiaId, disciplinaId, concursoId);
    }
}

