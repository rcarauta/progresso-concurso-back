package br.co.progresso.concurso.application.concurso;

import br.co.progresso.concurso.application.util.DataConverter;
import br.co.progresso.concurso.application.ConcursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;

import java.util.List;
import java.util.Optional;

@Service
public class ConcursoService {
	
	@Autowired
	private ConcursoRepository concursoRepostiory;
	
	@Autowired
	private ConcursoConverter concursoConverter;

	@Autowired
	private DataConverter dataConverter;
	
	@Transactional
	public ConcursoRequest salvar(ConcursoRequest concursoRequest) {
		Concurso concurso = concursoConverter.concursoRequestToConcurso(concursoRequest);
		concurso = concursoRepostiory.save(concurso);
		return concursoConverter.concursoToConcursoRequest(concurso); 
	}

	public List<ConcursoRequest> recuperarTodosConcursos() {
		List<Concurso> listaConcurso = concursoRepostiory.findAll();
		return concursoConverter.listConcursoToListConcursoRequest(listaConcurso);
	}

	public List<ConcursoRequest> recuperarTodosConcursosDoUsuario(Long userId) {
		List<Concurso> listaConcursoUsuario = concursoRepostiory.findByUserId(userId);
		return concursoConverter.listConcursoToListConcursoRequest(listaConcursoUsuario);
	}

	public ConcursoRequest buscarConcursoPeloId(Long id) {
		Concurso concurso = this.buscarConcurso(id);
		return concursoConverter.concursoToConcursoRequest(concurso);
	}

	@Transactional
	public ConcursoRequest atualizarConcurso(Long id, ConcursoRequest request) {
		 Concurso concurso = this.buscarConcurso(id);
		 Concurso concursoAtualizado = atualizarObjetoConcurso(concurso, request);
		 concurso = concursoRepostiory.save(concursoAtualizado);
		 return concursoConverter.concursoToConcursoRequest(concurso);
	}

	private Concurso atualizarObjetoConcurso(Concurso concurso, ConcursoRequest request) {
		concurso.setNome(request.getNome());
		concurso.setDataProvaDate(dataConverter.converterStringParaDate(request.getDataProvaDate()));
		concurso.setPercentualEstudadoFloat(request.getPercentualEstudadoFloat());
		concurso.setUser(concursoConverter.getUsuerPotrId(request.getUserId()));
		return concurso;
	}

	private Concurso buscarConcurso(Long id) {
		Optional<Concurso> concursoOptional = concursoRepostiory.findById(id);
		return concursoOptional.orElseThrow(() -> new ConcursoNaoEncontradoException(id));
	}
}
