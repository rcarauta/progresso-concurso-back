package br.co.progresso.concurso.application.concurso_disciplina;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.co.progresso.concurso.application.concurso.ConcursoRequest;

@RestController
@RequestMapping("/concurso_disciplina")
public class ConcursoDisciplinaController {

	@Autowired
	private ConcursoDisciplinaService concursoDisciplinaService;
	
	@PostMapping("/{concursoId}/associar")
	public ResponseEntity<ConcursoRequest> associarDisciplinas(@PathVariable Long concursoId,
			@RequestBody List<Long> disciplinaIds) {
		 ConcursoRequest request = concursoDisciplinaService.associarDisciplinas(concursoId, disciplinaIds);
		 return ResponseEntity.status(HttpStatus.OK).body(request);
		
	}
	
	@GetMapping("/{concursoId}/todas_disciplinas")
	public ResponseEntity<ConcursoRequest> verificaDisciplinasConcurso(@PathVariable Long concursoId) {
		ConcursoRequest request = concursoDisciplinaService.verificarDisciplinasConcurso(concursoId);
		return ResponseEntity.status(HttpStatus.OK).body(request);
	}
	
}