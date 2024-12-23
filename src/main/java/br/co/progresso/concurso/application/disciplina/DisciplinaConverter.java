package br.co.progresso.concurso.application.disciplina;

import br.co.progresso.concurso.infra.disciplina.Disciplina;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DisciplinaConverter {


    public Disciplina disciplinaRequestToDisciplina(DisciplinaRequest request) {
        Disciplina disciplina = new Disciplina();
        disciplina.setId(request.getId());
        disciplina.setNome(request.getNome());
        disciplina.setPorcentagem(request.getPorcentagem());
        disciplina.setCategoria(request.getCategoria());
        disciplina.setCiclos(request.getCiclos());
        return disciplina;
    }

    public DisciplinaRequest disciplinaToDisciplinaRequest(Disciplina disciplina) {
        return new DisciplinaRequest(disciplina.getId(),
                disciplina.getNome(), disciplina.getPorcentagem(),
                disciplina.getCategoria(), disciplina.getCiclos());
    }
    
    public List<DisciplinaRequest> listDisciplinaToListDisciplinaRequest(List<Disciplina> listaDisciplina) {
    	List<DisciplinaRequest> listaRequest = new ArrayList<>();
    	listaDisciplina.forEach(disciplina -> {
    		listaRequest.add(disciplinaToDisciplinaRequest(disciplina));
    	});
    	
		return listaRequest;
    }

}
