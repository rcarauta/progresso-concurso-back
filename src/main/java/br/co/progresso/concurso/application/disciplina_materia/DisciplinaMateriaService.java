package br.co.progresso.concurso.application.disciplina_materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaRepository;
import br.co.progresso.concurso.infra.disciplina_materia.DisciplinaMateriaDto;

@Service
public class DisciplinaMateriaService {

	@Autowired
	private ConcursoDisciplinaMateriaRepository concursoDisciplinaMateriaRepository;
	
	public List<DisciplinaMateriaDto> recuperaQuestoesPorDisciplina(Long concursoId) {
		return concursoDisciplinaMateriaRepository.findDisciplinaResumo(concursoId);
	}

}
