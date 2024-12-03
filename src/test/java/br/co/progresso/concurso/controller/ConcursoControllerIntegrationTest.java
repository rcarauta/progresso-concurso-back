package br.co.progresso.concurso.controller;

import br.co.progresso.concurso.request.ConcursoRequest;
import br.co.progresso.concurso.service.ConcursoService;
import br.co.progresso.concurso.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.SecretKey;

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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		request.setNome("Concurso ABC");
		request.setDataProvaDate("2024-12-10");
		request.setPercentualEstudadoFloat(75.0f);
		request.setUserId(1L);
		
		
		
		 String token = jwtService.generateToken("admin");

		// Executar o teste
		mockMvc.perform(post("/concurso").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.nome", is("Concurso ABC")))
				.andExpect(jsonPath("$.dataProvaDate", is("2024-12-10")))
				.andExpect(jsonPath("$.percentualEstudadoFloat", is(75.0)));
	}


}
