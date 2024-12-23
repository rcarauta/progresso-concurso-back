package br.co.progresso.concurso.application.disciplina;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.co.progresso.concurso.application.authentication.JwtService;

@SpringBootTest
@AutoConfigureMockMvc
public class ListarDisciplinaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisciplinaService disciplinaService;
    
    @Autowired
    private JwtService jwtService;

    @Test
    void listarTodasDisciplinas_DeveRetornarDuasDisciplinas() throws Exception {

        DisciplinaRequest disciplina1 = new DisciplinaRequest();
        disciplina1.setId(1L);
        disciplina1.setNome("Matemática");

        DisciplinaRequest disciplina2 = new DisciplinaRequest();
        disciplina2.setId(2L);
        disciplina2.setNome("Português");

        List<DisciplinaRequest> mockDisciplinaList = Arrays.asList(disciplina1, disciplina2);
        String token = jwtService.generateToken("admin", 1L);

        when(disciplinaService.listarTodasDisciplinas()).thenReturn(mockDisciplinaList);
        

        mockMvc.perform(get("/disciplina/list")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Matemática"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nome").value("Português"));


        verify(disciplinaService, times(1)).listarTodasDisciplinas();
    }
    
    @Test
    @DisplayName("Deve retornar disciplinas não associadas ao concurso")
    public void listarDisciplinasNaoAssociadasAoConcurso_deveRetornarDisciplinas() throws Exception {
        Long concursoId = 1L;
        
        DisciplinaRequest disciplina1 = new DisciplinaRequest(1L, "Matemática", 20.3F, CategoriaDisciplina.BASICA, 2);
        DisciplinaRequest disciplina2 = new DisciplinaRequest(2L, "Português", 20.3F, CategoriaDisciplina.BASICA, 1);
        String token = jwtService.generateToken("admin", 1L);

        List<DisciplinaRequest> disciplinasNaoAssociadas = Arrays.asList(disciplina1, disciplina2);
        when(disciplinaService.listarDisciplinasNaoAssociadasAoConcurso(concursoId))
                .thenReturn(disciplinasNaoAssociadas);

        mockMvc.perform(get("/disciplina/1/list_not_concurso")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Matemática"))
                .andExpect(jsonPath("$[1].nome").value("Português"));
    }
}

