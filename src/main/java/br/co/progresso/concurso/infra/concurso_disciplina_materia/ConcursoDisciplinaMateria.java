package br.co.progresso.concurso.infra.concurso_disciplina_materia;

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

   
    
}

