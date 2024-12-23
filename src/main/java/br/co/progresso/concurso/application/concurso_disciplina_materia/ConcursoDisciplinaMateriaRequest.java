package br.co.progresso.concurso.application.concurso_disciplina_materia;

public class ConcursoDisciplinaMateriaRequest {

	private Long concursoId;
	
	private Long disciplinaId;
	
	private Long materiaId;
	
	
	public ConcursoDisciplinaMateriaRequest() {
		
	}


	public ConcursoDisciplinaMateriaRequest(Long concursoId, Long disciplinaId, Long materiaId) {
		super();
		this.concursoId = concursoId;
		this.disciplinaId = disciplinaId;
		this.materiaId = materiaId;
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

	
	
	
}
