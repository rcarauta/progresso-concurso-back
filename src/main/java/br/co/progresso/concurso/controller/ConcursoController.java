package br.co.progresso.concurso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.co.progresso.concurso.request.ConcursoRequest;
import br.co.progresso.concurso.service.ConcursoService;

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
	
}
