package br.co.progresso.concurso.infra.disciplina;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.co.progresso.concurso.application.disciplina.CategoriaDisciplina;
import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "disciplina")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "O nome não pode ser vazio")
    private String nome;

    @Column
    private Float porcentagem;

    @ManyToMany(mappedBy = "disciplinas", fetch = FetchType.LAZY)
    private List<Concurso> concursos = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaDisciplina categoria;
    
    @Column(name = "ciclos")
    private Integer ciclos;
    
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Float getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Float porcentagem) {
        this.porcentagem = porcentagem;
    }

    public List<Concurso> getConcursos() {
        return concursos;
    }

    public void setConcursos(List<Concurso> concursos) {
        this.concursos = concursos;
    }

	public CategoriaDisciplina getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDisciplina categoria) {
		this.categoria = categoria;
	}

	public Integer getCiclos() {
		return ciclos;
	}

	public void setCiclos(Integer ciclos) {
		this.ciclos = ciclos;
	}

	public Set<ConcursoDisciplinaMateria> getContestDisciplinaMaterias() {
		return contestDisciplinaMaterias;
	}

	public void setContestDisciplinaMaterias(Set<ConcursoDisciplinaMateria> contestDisciplinaMaterias) {
		this.contestDisciplinaMaterias = contestDisciplinaMaterias;
	}
	
	
}
