package br.co.progresso.concurso.infra.concurso_disciplina;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import java.math.BigDecimal;

import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.disciplina.Disciplina;

@Entity
@Table(name = "contest_disciplina")
public class ConcursoDisciplina {

	@EmbeddedId
	private ConcursoDisciplinaId id;

	@Column(name = "ciclos", nullable = false)
	private int ciclos;

	@Column(name = "ordem")
	private Integer ordem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contest_id", insertable = false, updatable = false)
	private Concurso concurso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disciplina_id", insertable = false, updatable = false)
	private Disciplina disciplina;

	public ConcursoDisciplina() {
	}

	public ConcursoDisciplinaId getId() {
		return id;
	}

	public void setId(ConcursoDisciplinaId id) {
		this.id = id;
	}

	public int getCiclos() {
		return ciclos;
	}

	public void setCiclos(int ciclos) {
		this.ciclos = ciclos;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
}
