package br.co.progresso.concurso.application.concurso;

import br.co.progresso.concurso.application.ConcursoNaoEncontradoException;
import br.co.progresso.concurso.application.authentication.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcursoControllerListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConcursoService concursoService;

    @Autowired
    private JwtService jwtService;


    @Test
    void listarTodosConcursosTest() throws Exception {

        List<ConcursoRequest> concursoRequests = Arrays.asList(
                new ConcursoRequest(1L, "Concurso 1", "2024-12-12", 30.4f,1L),
                new ConcursoRequest(2L, "Concurso 2", "2024-13-12", 40.5f,2L)
        );
        Mockito.when(concursoService.recuperarTodosConcursos()).thenReturn(concursoRequests);

        String token = jwtService.generateToken("admin", 1L);

        mockMvc.perform(get("/concurso/list") .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1)) // Verifica o id do primeiro concurso
                .andExpect(jsonPath("$[0].nome").value("Concurso 1")) // Verifica o nome do primeiro concurso
                .andExpect(jsonPath("$[1].id").value(2)) // Verifica o id do segundo concurso
                .andExpect(jsonPath("$[1].nome").value("Concurso 2")); // Verifica o nome do segundo concurso
    }

    @Test
    void testListarTodosOsConcursosDoUsuario() throws Exception {
        Long userId = 1L;

        // Dados simulados que o serviço vai retornar
        List<ConcursoRequest> concursoList = Arrays.asList(
                new ConcursoRequest(1L, "Concurso A", "2024-12-12", 30.4f,1L),
                new ConcursoRequest(2L, "Concurso B", "2024-13-12", 40.5f,1L)
        );

        // Simulação do comportamento do serviço
        Mockito.when(concursoService.recuperarTodosConcursosDoUsuario(userId)).thenReturn(concursoList);

        String token = jwtService.generateToken("admin", 1L);

        // Simula a requisição GET para a URL /list/{userId} e valida a resposta
        mockMvc.perform(get("/concurso/list/{userId}", userId).header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verifica o status HTTP 200
                .andExpect(jsonPath("$.size()").value(concursoList.size())) // Verifica o tamanho da lista
                .andExpect(jsonPath("$[0].id").value(concursoList.get(0).getId())) // Verifica o id do primeiro concurso
                .andExpect(jsonPath("$[0].nome").value(concursoList.get(0).getNome())); // Verifica o nome do primeiro concurso
    }

    @Test
    public void shouldReturnConcursoWhenFound() throws Exception {
        Long contestId = 1L;

        ConcursoRequest concursoRequest = new ConcursoRequest();
        concursoRequest.setId(1L);
        concursoRequest.setNome("Concurso ABC");
        concursoRequest.setDataProvaDate("2024-12-10");
        concursoRequest.setPercentualEstudadoFloat(75.0f);
        concursoRequest.setUserId(1L);

        String token = jwtService.generateToken("admin", 1L);

        Mockito.when(concursoService.buscarConcursoPeloId(contestId)).thenReturn(concursoRequest);

        mockMvc.perform(get("/concurso/{contestId}", contestId).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(concursoRequest.getId()))
                .andExpect(jsonPath("$.nome").value(concursoRequest.getNome()))
                .andExpect(jsonPath("$.dataProvaDate").value(concursoRequest.getDataProvaDate()))
                .andExpect(jsonPath("$.percentualEstudadoFloat").value(concursoRequest.getPercentualEstudadoFloat()));
    }

    @Test
    public void shouldReturnNotFoundWhenConcursoNotFound() throws Exception {
        Long contestId = 1L;

        String token = jwtService.generateToken("admin", 1L);

        Mockito.when(concursoService.buscarConcursoPeloId(contestId)).thenThrow(new ConcursoNaoEncontradoException(contestId));

        mockMvc.perform(get("/concurso/{contestId}", contestId).header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Concurso não encontrado com o id: " + contestId));
    }

}
