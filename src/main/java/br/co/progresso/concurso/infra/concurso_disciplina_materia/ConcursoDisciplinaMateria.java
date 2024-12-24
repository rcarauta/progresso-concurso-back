package br.co.progresso.concurso.infra.concurso_disciplina_materia;

import java.time.LocalTime;

import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.materia.Materia;
import jakarta.persistence.*;

@Entity
@Table(name = "contest_disciplina_materia")
public class ConcursoDisciplinaMateria {

    @EmbeddedId
    private ConcursoDisciplinaMateriaId id;

    @ManyToOne
    @MapsId("materiaId")
    @JoinColumn(name = "materia_id", nullable = false)
    private Materia materia;

    @ManyToOne
    @MapsId("disciplinaId")
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    @ManyToOne
    @MapsId("concursoId")
    @JoinColumn(name = "concurso_id", nullable = false)
    private Concurso concurso;
    
    private Float porcentagem;
    
    @Column(name = "tempo_estudo")
    private LocalTime tempoEstudo;
    
    @Column(name = "total_questoes")
    private Integer totalQuestoes;
    
    @Column(name = "questoes_acertadas")
    private Integer questoesAcertadas;

	public ConcursoDisciplinaMateriaId getId() {
		return id;
	}

	public void setId(ConcursoDisciplinaMateriaId id) {
		this.id = id;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Concurso getConcurso() {
		return concurso;
	}

	public void setConcurso(Concurso concurso) {
		this.concurso = concurso;
	}

	public Float getPorcentagem() {
		return porcentagem;
	}

	public void setPorcentagem(Float porcentagem) {
		this.porcentagem = porcentagem;
	}

	public LocalTime getTempoEstudo() {
		return tempoEstudo;
	}

	public void setTempoEstudo(LocalTime tempoEstudo) {
		this.tempoEstudo = tempoEstudo;
	}

	public Integer getTotalQuestoes() {
		return totalQuestoes;
	}

	public void setTotalQuestoes(Integer totalQuestoes) {
		this.totalQuestoes = totalQuestoes;
	}

	public Integer getQuestoesAcertadas() {
		return questoesAcertadas;
	}

	public void setQuestoesAcertadas(Integer questoesAcertadas) {
		this.questoesAcertadas = questoesAcertadas;
	}
	
}

