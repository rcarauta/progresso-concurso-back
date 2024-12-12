package br.co.progresso.concurso.application.disciplina;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.co.progresso.concurso.application.DisciplinaDuplicadaException;
import br.co.progresso.concurso.application.authentication.JwtService;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class SalvarDisciplinaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DisciplinaService disciplinaService;

    @Autowired
    private JwtService jwtService;
  
    @Test
    public void salvarDisciplina_deveRetornarStatus201EObjetoSalvo() throws Exception {
        DisciplinaRequest request = new DisciplinaRequest();
        int randomNumber = (int)(Math.random() * 1_000_000_000) + 1;
        String disciplina = "Matemárica"+randomNumber;

        request.setNome(disciplina);
        request.setPorcentagem(60f);

        String jsonRequest = new ObjectMapper().writeValueAsString(request);
        String token = jwtService.generateToken("admin", 1L);

        MvcResult result = mockMvc.perform(post("/disciplina")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        DisciplinaRequest response = new ObjectMapper().readValue(responseContent, DisciplinaRequest.class);

        assertNotNull(response.getId());
        assertEquals(disciplina, response.getNome());
        assertEquals(60f, response.getPorcentagem());

        DisciplinaRequest disciplinaSalva = disciplinaService.buscarPorId(response.getId());
        assertEquals(60f, disciplinaSalva.getPorcentagem());
        assertEquals(disciplina, disciplinaSalva.getNome());
    }

}
