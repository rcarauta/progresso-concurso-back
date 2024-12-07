package br.co.progresso.concurso.application.disciplina;

import br.co.progresso.concurso.infra.disciplina.Disciplina;
import org.springframework.stereotype.Component;

@Component
public class DisciplinaConverter {


    public Disciplina disciplinaRequestToDisciplina(DisciplinaRequest request) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(request.getId());
        disciplina.setNome(request.getNome());
        disciplina.setPorcentagem(request.getPorcentagem());
        return disciplina;
    }

    public DisciplinaRequest disciplinaToDisciplinaRequest(Disciplina disciplina) {
        return new DisciplinaRequest(disciplina.getId(),
                disciplina.getNome(), disciplina.getPorcentagem());
    }

}
