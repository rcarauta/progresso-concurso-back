package br.co.progresso.concurso.request;

import java.util.Date;

public class ConcursoRequest {

	private Long id;
    private String nome;
    private String dataProvaDate;
    private Float percentualEstudadoFloat;
    private Long userId;

   
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
}
