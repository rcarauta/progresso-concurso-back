package br.co.progresso.concurso.application.disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.application.DisciplinaDuplicadaException;
import br.co.progresso.concurso.application.DisciplinaNaoEncontradaException;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaConverter disciplinaConverter;

    @Transactional
    public DisciplinaRequest salvarDisciplina(DisciplinaRequest request) {
    	validadeDisciplina(request);
        Disciplina disciplina = disciplinaConverter.disciplinaRequestToDisciplina(request);
        Disciplina disciplinaSalva = disciplinaRepository.save(disciplina);
        return disciplinaConverter.disciplinaToDisciplinaRequest(disciplinaSalva);
    }

	public DisciplinaRequest buscarPorId(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaNaoEncontradaException(id));
        return disciplinaConverter.disciplinaToDisciplinaRequest(disciplina);
    }
    
    public DisciplinaRequest buscarPorNome(String nome) {
    	Disciplina disciplina = disciplinaRepository.findByNome(nome);
    	return disciplinaConverter.disciplinaToDisciplinaRequest(disciplina);
    }
    
    private void validadeDisciplina(DisciplinaRequest request) {
		Disciplina verifica = disciplinaRepository.findByNome(request.getNome());
		if(verifica != null) {
			throw new DisciplinaDuplicadaException(request.getNome());
		}
	}

	public List<DisciplinaRequest> listarTodasDisciplinas() {
		List<Disciplina> listaDisciplina = disciplinaRepository.findAll();
		return disciplinaConverter.listDisciplinaToListDisciplinaRequest(listaDisciplina);
	}
	
	public List<DisciplinaRequest> listarDisciplinasNaoAssociadasAoConcurso(Long concursoId) {
		List<Disciplina> listaDisciplina = disciplinaRepository.findByNotIdConcurso(concursoId);
		return disciplinaConverter.listDisciplinaToListDisciplinaRequest(listaDisciplina);
	}

}
