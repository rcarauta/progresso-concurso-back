package br.co.progresso.concurso.application.concurso;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class ConcursoControllerUpdateIntegrationTest {

    @Mock
    private ConcursoService concursoService;

    @InjectMocks
    private ConcursoController concursoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(concursoController).build();
    }

    @Test
    public void shouldReturnUpdatedConcursoWhenPutRequestIsMade() throws Exception {
        ConcursoRequest request = new ConcursoRequest();
        request.setNome("Concurso Atualizado");
        request.setDataProvaDate("2024-12-10");
        request.setPercentualEstudadoFloat(80.0f);
        request.setUserId(1L);

        ConcursoRequest concursoAtualizado = new ConcursoRequest();
        concursoAtualizado.setId(1L);
        concursoAtualizado.setNome("Concurso Atualizado");
        concursoAtualizado.setDataProvaDate("2024-12-10");
        concursoAtualizado.setPercentualEstudadoFloat(80.0f);
        concursoAtualizado.setUserId(1L);

        when(concursoService.atualizarConcurso(anyLong(), any(ConcursoRequest.class))).thenReturn(concursoAtualizado);

        mockMvc.perform(put("/concurso/{id}", 1L)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())  // Espera o status 200 OK
                .andExpect(jsonPath("$.nome").value("Concurso Atualizado"))
                .andExpect(jsonPath("$.dataProvaDate").value("2024-12-10"))
                .andExpect(jsonPath("$.percentualEstudadoFloat").value(80.0))
                .andExpect(jsonPath("$.userId").value(1L));
    }
}

