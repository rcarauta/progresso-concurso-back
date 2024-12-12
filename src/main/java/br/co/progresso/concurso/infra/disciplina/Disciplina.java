package br.co.progresso.concurso.infra.disciplina;

import java.util.ArrayList;
import java.util.List;

import br.co.progresso.concurso.application.disciplina.CategoriaDisciplina;
import br.co.progresso.concurso.infra.concurso.Concurso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(mappedBy = "disciplinas")
    private List<Concurso> concursos = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaDisciplina categoria;


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
    
}
