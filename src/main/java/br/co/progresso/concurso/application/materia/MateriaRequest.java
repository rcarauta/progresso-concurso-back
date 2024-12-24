package br.co.progresso.concurso.application.materia;

import java.time.LocalTime;


public class MateriaRequest {

	private Long id;

	private String nome;
	
	private Float porcentagem;
	
	private LocalTime tempoEstudo;
	
	private Integer totalQuestoes;
	
	private Integer questoesAcertadas;

	public MateriaRequest() {

	}

	public MateriaRequest(Long id, String nome) {
		this.id = id;
		this.nome = nome;
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
