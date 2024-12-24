package br.co.progresso.concurso.infra.materia;

import java.time.LocalTime;
import java.util.Set;

import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "materia")
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome; 

    @OneToMany(mappedBy = "materia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ConcursoDisciplinaMateria> contestDisciplinaMaterias;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public Set<ConcursoDisciplinaMateria> getContestDisciplinaMaterias() {
		return contestDisciplinaMaterias;
	}

	public void setContestDisciplinaMaterias(Set<ConcursoDisciplinaMateria> contestDisciplinaMaterias) {
		this.contestDisciplinaMaterias = contestDisciplinaMaterias;
	}
    
}

