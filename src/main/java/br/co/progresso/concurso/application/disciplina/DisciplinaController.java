package br.co.progresso.concurso.application.disciplina;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<DisciplinaRequest> salvarDisciplina(@RequestBody DisciplinaRequest request) {
        DisciplinaRequest disciplinaSalva = disciplinaService.salvarDisciplina(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaSalva);
    }

}
