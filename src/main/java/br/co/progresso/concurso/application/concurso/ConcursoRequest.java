package br.co.progresso.concurso.application.concurso;

import java.util.List;

import br.co.progresso.concurso.infra.disciplina.Disciplina;

public class ConcursoRequest {

	private Long id;
    private String nome;
    private String dataProvaDate;
    private Float percentualEstudadoFloat;
    private Long userId;
    private List<Disciplina> listaDisciplinaEntity;

    public ConcursoRequest() {

    }

    public ConcursoRequest(Long id, String nome, String dataProvaDate,
                           Float percentualEstudadoFloat, Long userId) {
        this.id = id;
        this.nome = nome;
        this.dataProvaDate = dataProvaDate;
        this.percentualEstudadoFloat = percentualEstudadoFloat;
        this.userId = userId;

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

    public String getDataProvaDate() {
        return dataProvaDate;
    }

    public void setDataProvaDate(String dataProvaDate) {
        this.dataProvaDate = dataProvaDate;
    }

    public Float getPercentualEstudadoFloat() {
        return percentualEstudadoFloat;
    }

    public void setPercentualEstudadoFloat(Float percentualEstudadoFloat) {
        this.percentualEstudadoFloat = percentualEstudadoFloat;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public List<Disciplina> getListaDisciplinaEntity() {
		return listaDisciplinaEntity;
	}

	public void setListaDisciplinaEntity(List<Disciplina> listaDisciplinaEntity) {
		this.listaDisciplinaEntity = listaDisciplinaEntity;
	}
    
    
}
