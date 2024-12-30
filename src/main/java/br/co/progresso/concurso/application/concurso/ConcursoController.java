package br.co.progresso.concurso.application.concurso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/concurso")
public class ConcursoController {

	@Autowired
	public ConcursoService concursoService;

	@PostMapping
	public ResponseEntity<ConcursoRequest> salvarConcurso(@RequestBody ConcursoRequest request) {
		ConcursoRequest concursoRequest = concursoService.salvar(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(concursoRequest);
	}

	@GetMapping("/list")
	public ResponseEntity<List<ConcursoRequest>> listarTodosConcursos() {
		List<ConcursoRequest> request = concursoService.recuperarTodosConcursos();
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}

	@GetMapping("/list/{userId}")
	public ResponseEntity<List<ConcursoRequest>> listarTodosOsConcursosDoUsuario(@PathVariable("userId") Long userId) {
		List<ConcursoRequest> request = concursoService.recuperarTodosConcursosDoUsuario(userId);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}
	
	@GetMapping("/list_porcentagem/{userId}")
	public ResponseEntity<List<ConcursoRequest>> listaToodosOsConcursosPorcentagem(@PathVariable("userId") Long userId) {
		List<ConcursoRequest> listaRequest = concursoService.recuperaListaComPorcentagem(userId);
		return ResponseEntity.status(HttpStatus.OK).body(listaRequest);
	}
	
	@GetMapping("/{contestId}")
	public ResponseEntity<ConcursoRequest> buscarConcursoPeloId(@PathVariable("contestId") Long id) {
		ConcursoRequest request = concursoService.buscarConcursoPeloId(id);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ConcursoRequest> atualizarConcurso(@PathVariable("id") Long id,
			@RequestBody ConcursoRequest request) {
		ConcursoRequest atualizado = concursoService.atualizarConcurso(id, request);
		return ResponseEntity.status(HttpStatus.OK).body(atualizado);
	}
	
	@PostMapping("/clonar_concurso/{concursoId}")
	public ResponseEntity<ConcursoRequest> clonarConcurso(@PathVariable Long concursoId, @RequestBody List<Long> userDestinyId ) {
		ConcursoRequest cloneRequest = concursoService.clonarConcurso(concursoId, userDestinyId);
		return ResponseEntity.status(HttpStatus.OK).body(cloneRequest);
	}

}
