package br.co.progresso.concurso.application.disciplina;

import br.co.progresso.concurso.application.DisciplinaNaoEncontradaException;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private DisciplinaConverter disciplinaConverter;

    @Transactional
    public DisciplinaRequest salvarDisciplina(DisciplinaRequest request) {
        Disciplina disciplina = disciplinaConverter.disciplinaRequestToDisciplina(request);
        Disciplina disciplinaSalva = disciplinaRepository.save(disciplina);
        return disciplinaConverter.disciplinaToDisciplinaRequest(disciplinaSalva);
    }

    public DisciplinaRequest buscarPorId(Long id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaNaoEncontradaException(id));
        return disciplinaConverter.disciplinaToDisciplinaRequest(disciplina);
    }
}
