package br.co.progresso.concurso.application.materia;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.co.progresso.concurso.application.authentication.JwtService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MateriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JwtService jwtService;

    @Test
    void cadastrarMateria_DeveCriarMateriaERetornar201() throws Exception {

        MateriaRequest request = new MateriaRequest();
        request.setNome("Emprego de tempos e modos verbais");
        request.setPorcentagem(80.0f);
        request.setTempoEstudo(LocalTime.of(2, 30));

        String requestJson = objectMapper.writeValueAsString(request);
        String token = jwtService.generateToken("admin", 1L);

        mockMvc.perform(post("/materia")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Emprego de tempos e modos verbais"))
                .andExpect(jsonPath("$.porcentagem").value(80.0))
                .andExpect(jsonPath("$.tempoEstudo").value("02:30:00"));
        
    }
    
    @Test
    void atualizarMateriaComSucesso() throws Exception {
        MateriaRequest novaMateria = new MateriaRequest();
        novaMateria.setNome("Matemática");
        novaMateria.setPorcentagem(20.5f);
        novaMateria.setTempoEstudo(LocalTime.of(0, 40));

        String materiaJson = objectMapper.writeValueAsString(novaMateria);
        String token = jwtService.generateToken("admin", 1L);

        String responseJson = mockMvc.perform(post("/materia")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(materiaJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        MateriaRequest materiaSalva = objectMapper.readValue(responseJson, MateriaRequest.class);

        materiaSalva.setNome("Matemática Avançada");
        materiaSalva.setPorcentagem(30.5f);

        String materiaAtualizadaJson = objectMapper.writeValueAsString(materiaSalva);

        mockMvc.perform(put("/materia")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(materiaAtualizadaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matemática Avançada"))
                .andExpect(jsonPath("$.porcentagem").value(30.5f))
                .andExpect(jsonPath("$.tempoEstudo").value("00:40:00"));
    }
}
