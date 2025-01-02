package br.co.progresso.concurso.application.disciplina;

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
@RequestMapping("/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<DisciplinaRequest> salvarDisciplina(@RequestBody DisciplinaRequest request) {
        DisciplinaRequest disciplinaSalva = disciplinaService.salvarDisciplina(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplinaSalva);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<DisciplinaRequest>> listarTodasDisciplinas() {
    	List<DisciplinaRequest> listaDisciplina = disciplinaService.listarTodasDisciplinas();
    	return ResponseEntity.status(HttpStatus.OK).body(listaDisciplina);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{concursoId}/list_not_concurso")
    public ResponseEntity<List<DisciplinaRequest>> listarDisciplinasNaoAssociadasAoConcurso(@PathVariable Long concursoId) {
    	List<DisciplinaRequest> listaNotConcurso = disciplinaService.listarDisciplinasNaoAssociadasAoConcurso(concursoId);
    	return ResponseEntity.status(HttpStatus.OK).body(listaNotConcurso);
    }
    
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{concursoId}/porcentagem_disciplina")
    public ResponseEntity<List<DisciplinaRequest>> totalPorcentagemDisciplina(@PathVariable Long concursoId) {
    	List<DisciplinaRequest> listaRequest = disciplinaService.totalPorcentagemDisciplina(concursoId);
    	return ResponseEntity.status(HttpStatus.OK).body(listaRequest);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{disciplinaId}/alterar_disciplina")
    public ResponseEntity<DisciplinaRequest> alterarDisciplina(@PathVariable Long disciplinaId, 
    		@RequestBody DisciplinaRequest disciplinaResquest) {
    	DisciplinaRequest dr = disciplinaService.alterarDisciplina(disciplinaId, disciplinaResquest);
    	return ResponseEntity.status(HttpStatus.OK).body(dr);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{disciplinaId}/selecinar_disciplina")
    public ResponseEntity<DisciplinaRequest> selecionarDisciplina(@PathVariable Long disciplinaId) {
    	DisciplinaRequest dr = disciplinaService.buscarPorId(disciplinaId);
    	return ResponseEntity.status(HttpStatus.OK).body(dr);
    }
    

}
