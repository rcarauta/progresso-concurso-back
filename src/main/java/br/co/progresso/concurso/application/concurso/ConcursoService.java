package br.co.progresso.concurso.application.concurso;

import br.co.progresso.concurso.application.util.DataConverter;
import br.co.progresso.concurso.application.ConcursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.concurso_disciplina.ConcursoDisciplina;
import br.co.progresso.concurso.infra.concurso_disciplina.ConcursoDisciplinaId;
import br.co.progresso.concurso.infra.concurso_disciplina.ConcursoDisciplinaRepository;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaId;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.user.UserRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConcursoService {
	
	@Autowired
	private ConcursoRepository concursoRepostiory;
	
	@Autowired
	private ConcursoConverter concursoConverter;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private DataConverter dataConverter;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConcursoDisciplinaRepository concursoDisciplinaRepository;
	
	@Autowired
	private ConcursoDisciplinaMateriaRepository concursoDisciplinaMateriaRepository;
	
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
	
	public List<ConcursoRequest> recuperaListaComPorcentagem(Long userId) {
		List<Concurso> listaConcursoUsuario = concursoRepostiory.findByUserId(userId);
		List<ConcursoRequest> listaSemPorcentagem = concursoConverter.
				listConcursoToListConcursoRequest(listaConcursoUsuario);
		List<ConcursoRequest> listaConcursoPorcentagem = concursoRepostiory
				.findConcursoRequestsByUserId(userId);
		if(listaConcursoPorcentagem.size() == 0) {
			return listaSemPorcentagem;
		}
		listaConcursoPorcentagem = adicionarPorcentagemLista(listaSemPorcentagem, listaConcursoPorcentagem);
		return listaConcursoPorcentagem;
	}
	
	@Transactional
	public ConcursoRequest clonarConcurso(Long concursoId, List<Long> userDestinyId) {
		Concurso concurso = concursoRepostiory.findById(concursoId).get();
		Long userIdOne = userDestinyId.get(0);
		User user = userRepository.findById(userIdOne).get();
		concurso = salvarConcursoClone(concurso, user);
		
		salvarConcursoDisciplinaClone(concursoId, concurso);
		salvarConcursoDisciplinaMateriaClone(concursoId, concurso);
	
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
	
	private List<ConcursoRequest> adicionarPorcentagemLista(List<ConcursoRequest> listaSemPorcentagem, 
			List<ConcursoRequest> listaComPorcentagem) {
		List<ConcursoRequest> listaCompleta = new ArrayList<>();
		for(int i = 0; i < listaComPorcentagem.size(); i++) {
			ConcursoRequest concursoOriginal = listaSemPorcentagem.get(i);
			concursoOriginal.setPercentualEstudadoFloat(listaComPorcentagem.get(i).getPercentualEstudadoFloat());
			listaCompleta.add(concursoOriginal);
		}
		
		if(listaComPorcentagem.size() != listaSemPorcentagem.size()) {
			Integer diferenca = listaSemPorcentagem.size() - listaComPorcentagem.size();
			
			while(diferenca < listaComPorcentagem.size()) {
				diferenca++;
			}	
			for(;diferenca < listaSemPorcentagem.size(); diferenca++) {
				listaCompleta.add(listaSemPorcentagem.get(diferenca));
			}
		}
		return listaCompleta;
	}
	
	private Concurso salvarConcursoClone(Concurso concursoInicio, User user) {
		Concurso concursoClone = new Concurso();
		concursoClone.setNome(concursoInicio.getNome());
		concursoClone.setPercentualEstudadoFloat(1f);
		concursoClone.setDataProvaDate(concursoInicio.getDataProvaDate());
		concursoClone.setUser(user);
		concursoClone = concursoRepostiory.save(concursoClone);
		return concursoClone;
	}
	
	private void salvarConcursoDisciplinaClone(Long concursoId, Concurso concursoSalvo) {
		List<ConcursoDisciplina> listaDisciplinaConcurso = 
				concursoDisciplinaRepository.findAllDisciplinaByContest(concursoId);
		
		listaDisciplinaConcurso.forEach(disciplinaConcurso -> {
			ConcursoDisciplina salvarConcursoDisciplia = new ConcursoDisciplina();
			salvarConcursoDisciplia.setDisciplina(disciplinaConcurso.getDisciplina());
			salvarConcursoDisciplia.setConcurso(concursoSalvo);
			salvarConcursoDisciplia.setCiclos(0);
			ConcursoDisciplinaId id = new ConcursoDisciplinaId(concursoSalvo.getId(), disciplinaConcurso.getDisciplina().getId());
			salvarConcursoDisciplia.setId(id);
			
			ConcursoDisciplina salvar = concursoDisciplinaRepository.save(salvarConcursoDisciplia);
			System.out.println(salvar);
		});
	}
	
	private void salvarConcursoDisciplinaMateriaClone(Long concursoId, Concurso concursoSalvo) {
		List<ConcursoDisciplinaMateria> listaConcursoDisciplinaMateria = 
				concursoDisciplinaMateriaRepository.findByConcursoId(concursoId);
		
		listaConcursoDisciplinaMateria.forEach(concursoDisciplinaMateira -> {
			ConcursoDisciplinaMateria salvarMateria = new ConcursoDisciplinaMateria();
			salvarMateria.setConcurso(concursoSalvo);
			salvarMateria.setDisciplina(concursoDisciplinaMateira.getDisciplina());
			salvarMateria.setMateria(concursoDisciplinaMateira.getMateria());
			salvarMateria.setPorcentagem(0f);
			salvarMateria.setQuestoesAcertadas(0);
			salvarMateria.setTotalQuestoes(0);
			salvarMateria.setTempoEstudo(LocalTime.of(0, 0));

			ConcursoDisciplinaMateriaId id = new ConcursoDisciplinaMateriaId(
					concursoDisciplinaMateira.getMateria().getId(), 
					concursoDisciplinaMateira.getDisciplina().getId(),
					concursoSalvo.getId());
			salvarMateria.setId(id);

			ConcursoDisciplinaMateria salvar = concursoDisciplinaMateriaRepository.save(salvarMateria);
			System.out.println(salvar);
		});
	}
}
