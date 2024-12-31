package br.co.progresso.concurso.application.concurso_disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.co.progresso.concurso.application.concurso.ConcursoRequest;

@RestController
@RequestMapping("/concurso_disciplina")
public class ConcursoDisciplinaController {

	@Autowired
	private ConcursoDisciplinaService concursoDisciplinaService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{concursoId}/associar")
	public ResponseEntity<ConcursoRequest> associarDisciplinas(@PathVariable Long concursoId,
			@RequestBody List<Long> disciplinaIds) {
		 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		 System.out.println("Contexto de segurança: " + auth);
		 ConcursoRequest request = concursoDisciplinaService.associarDisciplinas(concursoId, disciplinaIds);
		 return ResponseEntity.status(HttpStatus.OK).body(request);
		
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{concursoId}/todas_disciplinas")
	public ResponseEntity<ConcursoRequest> verificaDisciplinasConcurso(@PathVariable Long concursoId) {
		ConcursoRequest request = concursoDisciplinaService.verificarDisciplinasConcurso(concursoId);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{concursoId}/{disciplinaId}/ordenar") 
	public ResponseEntity<ConcursoRequest> ordenarDisciplinaConcurso(@PathVariable Long concursoId,
			@PathVariable Long disciplinaId, @RequestBody List<Integer> numeroOrdem) {
		ConcursoRequest updateRequest = concursoDisciplinaService.ordenarDisciplinaConcurso(
				concursoId, disciplinaId , numeroOrdem.get(0));
		return ResponseEntity.status(HttpStatus.OK).body(updateRequest);
	}
	
	@PreAuthorize("hasRole('USER')")
	@GetMapping("/{concursoId}/listar_ordem")
	public ResponseEntity<ConcursoRequest> listaOrdenadaDisciplinaConcurso(@PathVariable Long concursoId) {
		ConcursoRequest listCooncursoDisciplina = concursoDisciplinaService
				.listaOrdenadaDisciplinaConcurso(concursoId);
		return ResponseEntity.status(HttpStatus.OK).body(listCooncursoDisciplina);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PutMapping("/{concursoId}/{disciplinaId}/atualizar_ciclo")
	public ResponseEntity<ConcursoRequest> atualizarCiclosDisciplina(@PathVariable Long concursoId,
			@PathVariable Long disciplinaId, @RequestBody List<Integer> numeroCiclo) {
		ConcursoRequest concursoDisciplina = concursoDisciplinaService.
				atualizarCiclosDisciplina(concursoId, disciplinaId, numeroCiclo.get(0));
		return ResponseEntity.status(HttpStatus.OK).body(concursoDisciplina);
	}
	
	
}
