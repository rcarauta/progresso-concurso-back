package br.co.progresso.concurso.application.concurso_disciplina_materia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.co.progresso.concurso.application.materia.MateriaRequest;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;

@Component
public class ConcursoDisciplinaMateriaConverter {
	
	public ConcursoDisciplinaMateriaRequest concursoMateriaToRequest(ConcursoDisciplinaMateria materia) {
		ConcursoDisciplinaMateriaRequest request = 
				new ConcursoDisciplinaMateriaRequest(materia.getConcurso().getId(),
						materia.getDisciplina().getId(), 
						materia.getMateria().getId(), materia.getPorcentagem(),
						materia.getTempoEstudo());
		return request;
	}
	
	public List<MateriaRequest> addValueConcursoDisciplinaMateriaToMateriaRequest(List<ConcursoDisciplinaMateria> requestList) {
		List<MateriaRequest> listaMateria = new ArrayList<>();
		requestList.forEach(concurso -> {
			MateriaRequest materia = new MateriaRequest();
			materia.setId(concurso.getMateria().getId());
			materia.setNome(concurso.getMateria().getNome());
			materia.setPorcentagem(concurso.getPorcentagem());
			materia.setTempoEstudo(concurso.getTempoEstudo());
			materia.setTotalQuestoes(concurso.getTotalQuestoes());
			materia.setQuestoesAcertadas(concurso.getQuestoesAcertadas());
			listaMateria.add(materia);	
		});
		
		return listaMateria;
	}
	
	
	public ConcursoDisciplinaMateria modificarPorcentagem(MateriaRequest request, ConcursoDisciplinaMateria concurso) {
		LocalTime tempoEstudoMateria = concurso.getTempoEstudo();
		LocalTime tempoEstudoRequest = request.getTempoEstudo();
		
		tempoEstudoMateria = tempoEstudoMateria == null ? LocalTime.of(0,0) : tempoEstudoMateria;
		
		tempoEstudoRequest = tempoEstudoRequest == null ? LocalTime.of(0,0) : tempoEstudoRequest;
		
		long minutosTotais = tempoEstudoMateria.toSecondOfDay() + tempoEstudoRequest.toSecondOfDay();
		
		concurso.setTempoEstudo(LocalTime.ofSecondOfDay(minutosTotais));
		
		return concurso;
	}

	
}
