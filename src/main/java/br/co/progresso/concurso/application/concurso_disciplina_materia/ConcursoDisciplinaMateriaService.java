package br.co.progresso.concurso.application.concurso_disciplina_materia;

import java.util.List;

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
		concursoDisciplinaMateria.setPorcentagem(0f);
		
		return concursoDisciplinaMateria;
	}


	public ConcursoDisciplinaMateriaRequest alterarMateriaAoConcursoDisciplina(Long concursoId, Long disciplinaId,
			MateriaRequest materiaRequest) {
		ConcursoDisciplinaMateriaId id = new ConcursoDisciplinaMateriaId(materiaRequest.getId(), disciplinaId, concursoId);
		ConcursoDisciplinaMateria concursoDisciplinaMateria = concursoDisciplinaMateriaRepository.findById(id).get();
		concursoDisciplinaMateria.setPorcentagem(materiaRequest.getPorcentagem());
		concursoDisciplinaMateria = concursoDisciplinaMateriaConverter.modificarPorcentagem(materiaRequest, concursoDisciplinaMateria);
		concursoDisciplinaMateria.setTotalQuestoes(materiaRequest.getTotalQuestoes());
		concursoDisciplinaMateria.setQuestoesAcertadas(materiaRequest.getQuestoesAcertadas());
		concursoDisciplinaMateria = concursoDisciplinaMateriaRepository.save(concursoDisciplinaMateria);
		return concursoDisciplinaMateriaConverter.concursoMateriaToRequest(concursoDisciplinaMateria);
	}


	public List<MateriaRequest> recuperaMateriaRequestComPorcentagem(Long disciplinaId,
			Long concursoId) {
		List<ConcursoDisciplinaMateria> listaConcursoDisciplinaMateria = concursoDisciplinaMateriaRepository
				.findByIdConcursoIdAndIdDisciplinaId(concursoId, disciplinaId);
		
		List<MateriaRequest> requestList = concursoDisciplinaMateriaConverter
				.addValueConcursoDisciplinaMateriaToMateriaRequest(listaConcursoDisciplinaMateria);

		return requestList;
	}


	public List<ConcursoDisciplinaMateriaRequest> desassociarMateriaDisciplina(Long concursoId, Long disciplinaId,
			Long materiaId) {
		ConcursoDisciplinaMateriaId id = new ConcursoDisciplinaMateriaId(materiaId, disciplinaId, concursoId);
		ConcursoDisciplinaMateria paraDeletar = concursoDisciplinaMateriaRepository.findById(id).get();
		concursoDisciplinaMateriaRepository.delete(paraDeletar);
		List<ConcursoDisciplinaMateria> lista = concursoDisciplinaMateriaRepository.
				findByConcursoId(concursoId);
		List<ConcursoDisciplinaMateriaRequest> listaRequest = concursoDisciplinaMateriaConverter
				.listaDisciplinaToRequest(lista);
				
		return listaRequest;
	}
	
	
}
