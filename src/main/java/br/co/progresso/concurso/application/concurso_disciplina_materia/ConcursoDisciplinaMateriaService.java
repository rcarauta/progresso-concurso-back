package br.co.progresso.concurso.application.concurso_disciplina_materia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.application.materia.MateriaRequest;
import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaId;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import br.co.progresso.concurso.infra.materia.Materia;
import br.co.progresso.concurso.infra.materia.MateriaRepository;

@Service
public class ConcursoDisciplinaMateriaService {

	@Autowired
	private ConcursoDisciplinaMateriaRepository concursoDisciplinaMateriaRepository;
	
	@Autowired
	private ConcursoRepository concursoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	@Autowired
	private ConcursoDisciplinaMateriaConverter concursoDisciplinaMateriaConverter;
	
	@Transactional
	public ConcursoDisciplinaMateriaRequest associarMateriaAoConcursoDisciplina(Long concursoId, Long disciplinaId,
			MateriaRequest materiaRequest) {
		ConcursoDisciplinaMateria concursoDisciplinaMateria = 
				criarConcursoDiusciplinaMateria(concursoId, disciplinaId, materiaRequest.getId());
		 concursoDisciplinaMateria = concursoDisciplinaMateriaRepository.save(concursoDisciplinaMateria);
		 
		return concursoDisciplinaMateriaConverter.concursoMateriaToRequest(concursoDisciplinaMateria);
	}


	private ConcursoDisciplinaMateria criarConcursoDiusciplinaMateria(Long concursoId, Long disciplinaId, Long materiaId) {
		ConcursoDisciplinaMateria concursoDisciplinaMateria = new ConcursoDisciplinaMateria();
		ConcursoDisciplinaMateriaId id = new ConcursoDisciplinaMateriaId(materiaId, disciplinaId, concursoId);
		concursoDisciplinaMateria.setId(id);
		Concurso concurso = concursoRepository.findById(concursoId).get();
		concursoDisciplinaMateria.setConcurso(concurso);
		Disciplina disciplina = disciplinaRepository.findById(disciplinaId).get();
		concursoDisciplinaMateria.setDisciplina(disciplina);
		Materia materia = materiaRepository.findById(materiaId).get();
		concursoDisciplinaMateria.setMateria(materia);
		
		return concursoDisciplinaMateria;
	}

	
	
}
