package br.co.progresso.concurso.application.concurso_disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.application.ConcursoNaoEncontradoException;
import br.co.progresso.concurso.application.concurso.ConcursoConverter;
import br.co.progresso.concurso.application.concurso.ConcursoRequest;
import br.co.progresso.concurso.application.disciplina.DisciplinaConverter;
import br.co.progresso.concurso.application.disciplina.DisciplinaRequest;
import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.concurso_disciplina.ConcursoDisciplina;
import br.co.progresso.concurso.infra.concurso_disciplina.ConcursoDisciplinaId;
import br.co.progresso.concurso.infra.concurso_disciplina.ConcursoDisciplinaRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;

@Service
public class ConcursoDisciplinaService {
	
	@Autowired
	private ConcursoRepository concursoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private ConcursoConverter concursoConverter;
	
	@Autowired
	private DisciplinaConverter disciplinaConverter;
	
	@Autowired
	private ConcursoDisciplinaRepository concursoDisciplinaRepository;


	@Transactional
	public ConcursoRequest associarDisciplinas(Long concursoId, List<Long> disciplinaIds) {
		Concurso concurso = concursoRepository.findById(concursoId)
                .orElseThrow(() -> new ConcursoNaoEncontradoException(concursoId));

        List<Disciplina> disciplinas = disciplinaRepository.findAllById(disciplinaIds);
        concurso.getDisciplinas().addAll(disciplinas);
        
       Concurso concursoSalvo = concursoRepository.save(concurso);
       
       ConcursoRequest concursoRequest = concursoConverter.concursoToConcursoRequest(concursoSalvo);
       concursoRequest.setListaDisciplinaEntity(disciplinas);
          
       return concursoRequest;
	}

	public ConcursoRequest verificarDisciplinasConcurso(Long concursoId) {
		Concurso concurso = concursoRepository.findById(concursoId)
                .orElseThrow(() -> new ConcursoNaoEncontradoException(concursoId));
		
		List<Disciplina> listaDisciplina = disciplinaRepository.findBayIdConcurso(concurso.getId());
		listaDisciplina.forEach(d -> d.setConcursos(null));
		ConcursoRequest request = concursoConverter.concursoToConcursoRequest(concurso);
		request.setListaDisciplinaRequest(disciplinaConverter.listDisciplinaToListDisciplinaRequest(listaDisciplina, concursoId));
		
		return request;
	}

	public ConcursoRequest ordenarDisciplinaConcurso(Long concursoId, Long disciplinaId, Integer numeroOrdem) {
		ConcursoDisciplinaId id = new ConcursoDisciplinaId(concursoId, disciplinaId);
		ConcursoDisciplina disciplina = concursoDisciplinaRepository.findById(id).get();
		disciplina.setOrdem(numeroOrdem);
		concursoDisciplinaRepository.save(disciplina);
		ConcursoRequest request = new ConcursoRequest();
		request.setOrdem(disciplina.getOrdem());	
		return request;
	}

	public ConcursoRequest listaOrdenadaDisciplinaConcurso(Long concursoId) {
		List<Disciplina> listaDisciplina = concursoDisciplinaRepository.findDisciplinasByContestIdWithOrder(concursoId);
		ConcursoRequest concursoRequest = new ConcursoRequest();
		List<DisciplinaRequest> listaDisciplinaRequest = disciplinaConverter.listDisciplinaToListDisciplinaRequest(listaDisciplina);
		concursoRequest.setListaDisciplinaRequest(listaDisciplinaRequest);
		return concursoRequest;
	}
	

}
