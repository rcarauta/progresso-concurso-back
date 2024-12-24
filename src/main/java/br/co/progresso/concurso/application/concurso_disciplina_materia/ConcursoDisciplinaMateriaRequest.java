package br.co.progresso.concurso.application.concurso_disciplina_materia;

import java.time.LocalTime;

public class ConcursoDisciplinaMateriaRequest {

	private Long concursoId;
	
	private Long disciplinaId;
	
	private Long materiaId;
	
	private Float porcentagem;

	private LocalTime tempoEstudo;
	
	
	public ConcursoDisciplinaMateriaRequest() {
		
	}


	public ConcursoDisciplinaMateriaRequest(Long concursoId, Long disciplinaId, Long materiaId,
			Float porcentagem, LocalTime tempoEstudo) {
		this.concursoId = concursoId;
		this.disciplinaId = disciplinaId;
		this.materiaId = materiaId;
		this.porcentagem = porcentagem;
		this.tempoEstudo = tempoEstudo;
	}



	public Long getConcursoId() {
		return concursoId;
	}


	public void setConcursoId(Long concursoId) {
		this.concursoId = concursoId;
	}


	public Long getDisciplinaId() {
		return disciplinaId;
	}


	public void setDisciplinaId(Long disciplinaId) {
		this.disciplinaId = disciplinaId;
	}


	public Long getMateriaId() {
		return materiaId;
	}


	public void setMateriaId(Long materiaId) {
		this.materiaId = materiaId;
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
	
}
