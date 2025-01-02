package br.co.progresso.concurso.application.materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materia")
public class MateriaController {
	
	@Autowired
	private MateriaService materiaService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<MateriaRequest> cadastrarMateria(@RequestBody MateriaRequest request) {
		MateriaRequest materiaRequest = materiaService.salvarMateria(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(materiaRequest);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{concursoId}/{disciplinaId}/list")
	public ResponseEntity<List<MateriaRequest>> listarTodasMaterias(@PathVariable Long concursoId,
			@PathVariable Long disciplinaId) {
		List<MateriaRequest> listaNaoAssociada = materiaService.listarTodasMaterias(concursoId, disciplinaId);
		return ResponseEntity.status(HttpStatus.OK).body(listaNaoAssociada);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{concursoId}/{disciplinaId}/list_associate")
	public ResponseEntity<List<MateriaRequest>> listarTodasMateriasAssociadas(@PathVariable Long concursoId,
			@PathVariable Long disciplinaId) {
		List<MateriaRequest> listaNaoAssociada = materiaService.listarTodasMateriasAssociadas(concursoId, disciplinaId);
		return ResponseEntity.status(HttpStatus.OK).body(listaNaoAssociada);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/atualizar")
	public ResponseEntity<MateriaRequest> atualizarMateria(@RequestBody MateriaRequest materiaRequest) {
		MateriaRequest request = materiaService.atualizarMateria(materiaRequest);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{materiaId}/recuperar_materia")
	public ResponseEntity<MateriaRequest> recuperarMateria(@PathVariable Long materiaId) {
		MateriaRequest request = materiaService.recuperarMateria(materiaId);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}
	
}
