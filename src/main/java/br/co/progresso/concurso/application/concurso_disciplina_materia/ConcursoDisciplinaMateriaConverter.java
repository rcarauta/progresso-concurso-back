package br.co.progresso.concurso.application.concurso_disciplina_materia;

import org.springframework.stereotype.Component;

import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;

@Component
public class ConcursoDisciplinaMateriaConverter {

	public ConcursoDisciplinaMateriaRequest concursoMateriaToRequest(ConcursoDisciplinaMateria materia) {
		ConcursoDisciplinaMateriaRequest request = 
				new ConcursoDisciplinaMateriaRequest(materia.getConcurso().getId(),
						materia.getDisciplina().getId(), 
						materia.getMateria().getId());
		return request;
	}
	
}
