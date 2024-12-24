package br.co.progresso.concurso.application.materia;

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
		return materia;
	}
	
	public MateriaRequest materiaToMateriaRequest(Materia materia) {
		MateriaRequest request = new MateriaRequest(materia.getId(),
				materia.getNome());
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
		return materia;
	}
	

}
