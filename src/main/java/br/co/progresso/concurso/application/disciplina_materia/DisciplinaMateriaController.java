package br.co.progresso.concurso.application.disciplina_materia;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.co.progresso.concurso.infra.disciplina_materia.DisciplinaMateriaDto;

@RestController
@RequestMapping("/disciplina_materia")
public class DisciplinaMateriaController {

	@Autowired
	private DisciplinaMateriaService disciplinaMateriaService;
	
	@GetMapping("/{concursoId}/questoes_total")
	public ResponseEntity<List<DisciplinaMateriaDto>> recuperarQuestoesPorDisciplina(@PathVariable Long concursoId) {
		List<DisciplinaMateriaDto> listaDisciplinaMateria = disciplinaMateriaService.
				recuperaQuestoesPorDisciplina(concursoId);
		return ResponseEntity.status(HttpStatus.OK).body(listaDisciplinaMateria);
	}
	
}
