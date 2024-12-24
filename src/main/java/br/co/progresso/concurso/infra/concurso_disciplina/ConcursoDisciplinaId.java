package br.co.progresso.concurso.infra.concurso_disciplina;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ConcursoDisciplinaId implements Serializable {

	@Column(name = "contest_id")
    private Long contestId;
	@Column(name = "disciplina_id")
    private Long disciplinaId;

    public ConcursoDisciplinaId() {}

    public ConcursoDisciplinaId(Long contestId, Long disciplinaId) {
        this.contestId = contestId;
        this.disciplinaId = disciplinaId;
    }

    public Long getContestId() {
        return contestId;
    }

    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    public Long getDisciplinaId() {
        return disciplinaId;
    }

    public void setDisciplinaId(Long disciplinaId) {
        this.disciplinaId = disciplinaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConcursoDisciplinaId that = (ConcursoDisciplinaId) o;
        return Objects.equals(contestId, that.contestId) &&
               Objects.equals(disciplinaId, that.disciplinaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contestId, disciplinaId);
    }
}
