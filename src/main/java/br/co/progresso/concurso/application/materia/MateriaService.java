package br.co.progresso.concurso.application.materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.infra.materia.Materia;
import br.co.progresso.concurso.infra.materia.MateriaRepository;

@Service
public class MateriaService {

	@Autowired
	private MateriaConverter materiaConverter;
	
	@Autowired
	private MateriaRepository materiaRepository;
	
	
	@Transactional
	public MateriaRequest salvarMateria(MateriaRequest request) {
		Materia materia = materiaConverter.materiaRequestToMateria(request);
		Materia materiaSalva = materiaRepository.save(materia);
		return materiaConverter.materiaToMateriaRequest(materiaSalva);
	}


	public List<MateriaRequest> listarTodasMaterias(Long concursoId, Long disciplinaId) {
		List<Materia> listaMateria = materiaRepository.listarTodasAsMaterias(concursoId, disciplinaId);
		return materiaConverter.listConcursoToListConcursoRequest(listaMateria);
	}


	public List<MateriaRequest> listarTodasMateriasAssociadas(Long concursoId, Long disciplinaId) {
		List<Materia> listaMateria = materiaRepository.listarTodasAsMateriasAssociadas(concursoId, disciplinaId);
		return materiaConverter.listConcursoToListConcursoRequest(listaMateria);
	}


	public MateriaRequest atualizarMateria(MateriaRequest materiaRequest) {
		Materia materia = materiaRepository.findById(materiaRequest.getId()).get();
		materia = materiaConverter.addMateriaRequestInMateria(materia, materiaRequest);
		materia = materiaRepository.save(materia);
		MateriaRequest materiaRequestSalva = materiaConverter.materiaToMateriaRequest(materia);
		return materiaRequestSalva;
	}


	public MateriaRequest recuperarMateria(Long materiaId) {
		Materia materia = materiaRepository.findById(materiaId).get();
		MateriaRequest request = materiaConverter.materiaToMateriaRequest(materia);
		return request;
	}
	
}
