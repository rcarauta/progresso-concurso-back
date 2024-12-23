package br.co.progresso.concurso.application.materia;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.co.progresso.concurso.infra.materia.Materia;

@Component
public class MateriaConverter {
	
	
	public Materia materiaRequestToMateria(MateriaRequest request) {
		Materia materia = new Materia();
		materia.setId(request.getId());
		materia.setNome(request.getNome());
		materia.setPorcentagem(request.getPorcentagem());
		materia.setTempoEstudo(request.getTempoEstudo());
		return materia;
	}
	
	public MateriaRequest materiaToMateriaRequest(Materia materia) {
		MateriaRequest request = new MateriaRequest(materia.getId(),
				materia.getNome(), materia.getPorcentagem(), materia.getTempoEstudo());
		return request;
	}
	
	public List<MateriaRequest> listConcursoToListConcursoRequest(List<Materia> listaMateria) {
		List<MateriaRequest> request = new ArrayList<>();
		listaMateria.forEach(materia -> {
			request.add(materiaToMateriaRequest(materia));
		});
		return request;
	}
	
	public Materia addMateriaRequestInMateria(Materia materia, MateriaRequest request) {
		materia.setNome(request.getNome());
		materia.setPorcentagem(request.getPorcentagem());
		
	    LocalTime tempoEstudoMateria = materia.getTempoEstudo();
	    LocalTime tempoEstudoRequest = request.getTempoEstudo();
	    
	    long minutosTotais = tempoEstudoMateria.toSecondOfDay() + tempoEstudoRequest.toSecondOfDay();
		
		materia.setTempoEstudo(LocalTime.ofSecondOfDay(minutosTotais));
		return materia;
	}
	

}
