package br.co.progresso.concurso.application.concurso;

import br.co.progresso.concurso.application.authentication.JwtService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ConcursoControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ConcursoService concursoService;
	
	@Autowired
    private JwtService jwtService;


	@Test
	public void shouldCreateConcurso() throws Exception {
		// Criar uma requisição de teste
		ConcursoRequest request = new ConcursoRequest();
		request.setNome("Concurso ABC");
		request.setDataProvaDate("2030-12-10");
		request.setPercentualEstudadoFloat(75.0f);
		request.setUserId(1L);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L);

		// Executar o teste
		mockMvc.perform(post("/concurso")
						.header("Authorization", "Bearer " + token) // Cabeçalho com o token JWT
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request))) // Corpo da requisição em JSON
				.andExpect(status().isCreated()) // Verifica se o status é 201 (Created)
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.nome").value("Concurso ABC")) // Verifica o campo "nome"
				.andExpect(jsonPath("$.dataProvaDate").value("2030-12-10")) // Verifica a data
				.andExpect(jsonPath("$.percentualEstudadoFloat").value(75.0f)); // Verifica o percentual
	}

}
