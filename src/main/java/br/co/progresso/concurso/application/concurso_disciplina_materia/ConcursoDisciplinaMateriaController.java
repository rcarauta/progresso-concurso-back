package br.co.progresso.concurso.application.concurso_disciplina_materia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.co.progresso.concurso.application.materia.MateriaRequest;

@RestController
@RequestMapping("/concurso_disciplina_materia")
public class ConcursoDisciplinaMateriaController {

	@Autowired
	private ConcursoDisciplinaMateriaService concursoDisciplinaMateriaService;
	
	@PostMapping("{concursoId}/{disciplinaId}/associar")
	public ResponseEntity<ConcursoDisciplinaMateriaRequest> associarMatriaAoConcursoDisciplina(
			@PathVariable Long concursoId, @PathVariable Long disciplinaId, @RequestBody MateriaRequest materiaRequest) {
		ConcursoDisciplinaMateriaRequest request = concursoDisciplinaMateriaService.associarMateriaAoConcursoDisciplina(
				concursoId, disciplinaId, materiaRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(request);
	}
	
}
