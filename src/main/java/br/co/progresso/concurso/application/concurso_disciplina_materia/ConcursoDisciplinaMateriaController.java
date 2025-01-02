package br.co.progresso.concurso.application.concurso_disciplina_materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.co.progresso.concurso.application.materia.MateriaRequest;

@RestController
@RequestMapping("/concurso_disciplina_materia")
public class ConcursoDisciplinaMateriaController {

	@Autowired
	private ConcursoDisciplinaMateriaService concursoDisciplinaMateriaService;
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("{concursoId}/{disciplinaId}/associar")
	public ResponseEntity<ConcursoDisciplinaMateriaRequest> associarMatriaAoConcursoDisciplina(
			@PathVariable Long concursoId, @PathVariable Long disciplinaId, @RequestBody MateriaRequest materiaRequest) {
		ConcursoDisciplinaMateriaRequest request = concursoDisciplinaMateriaService.associarMateriaAoConcursoDisciplina(
				concursoId, disciplinaId, materiaRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(request);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("{concursoId}/{disciplinaId}/alterar")
	public ResponseEntity<ConcursoDisciplinaMateriaRequest> alterarMateriaAoConcursoDisciplina(
			@PathVariable Long concursoId, @PathVariable Long disciplinaId, @RequestBody MateriaRequest materiaRequest) {
		ConcursoDisciplinaMateriaRequest request = concursoDisciplinaMateriaService.alterarMateriaAoConcursoDisciplina(
				concursoId, disciplinaId, materiaRequest);
		return ResponseEntity.status(HttpStatus.OK).body(request);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("{concursoId}/{disciplinaId}/recuperar")
	public ResponseEntity<List<MateriaRequest>> recuperarMateriaAoConcursoDisciplina(
			@PathVariable Long concursoId, @PathVariable Long disciplinaId) {
		List<MateriaRequest> request = concursoDisciplinaMateriaService.recuperaMateriaRequestComPorcentagem(disciplinaId, 
				concursoId);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("{concursoId}/{disciplinaId}/{materiaId}/desassociar")
	public ResponseEntity<List<ConcursoDisciplinaMateriaRequest>> desassociarMateriaDaDisciplina(@PathVariable Long concursoId,
			@PathVariable Long disciplinaId, @PathVariable Long materiaId) {
		List<ConcursoDisciplinaMateriaRequest> listaRequest = concursoDisciplinaMateriaService.desassociarMateriaDisciplina(
				concursoId, disciplinaId, materiaId);
		return ResponseEntity.status(HttpStatus.OK).body(listaRequest);
	}
	
}
