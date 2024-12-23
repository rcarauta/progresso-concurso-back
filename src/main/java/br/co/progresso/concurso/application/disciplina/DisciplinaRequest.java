package br.co.progresso.concurso.application.disciplina;

public class DisciplinaRequest {
    private Long id;
    private String nome;
    private Float porcentagem;
    private CategoriaDisciplina categoria;
    private Integer ciclos;

    public DisciplinaRequest() {

    }

    public DisciplinaRequest(Long id, String nome, Float porcentagem, CategoriaDisciplina categoria, Integer ciclos) {
        this.id = id;
        this.nome = nome;
        this.porcentagem = porcentagem;
        this.categoria = categoria;
        this.ciclos = ciclos;
    }

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
	    
}
