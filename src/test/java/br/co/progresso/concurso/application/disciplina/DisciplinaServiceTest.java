package br.co.progresso.concurso.application.disciplina;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.co.progresso.concurso.application.DisciplinaDuplicadaException;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;


@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;
    
    @Mock
    private DisciplinaConverter disciplinaConverter;
    
    @Autowired
    private DisciplinaService disciplinaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void salvarDisciplina_DeveLancarExcecaoQuandoDisciplinaDuplicada() {

        String nomeDisciplina = "Matematica";
        DisciplinaRequest request = new DisciplinaRequest();
        request.setNome(nomeDisciplina);
        request.setPorcentagem(70.0f);

        Disciplina disciplinaExistente = new Disciplina();
        disciplinaExistente.setNome(nomeDisciplina);
        when(disciplinaRepository.findByNome(nomeDisciplina)).thenReturn(disciplinaExistente);

        DisciplinaDuplicadaException exception = assertThrows(DisciplinaDuplicadaException.class, () -> {
            disciplinaService.salvarDisciplina(request);
        });

        assertEquals("Disciplina duplicada: Matematica", exception.getMessage());
       
    }
}
