package br.co.progresso.concurso.application.authentication;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.co.progresso.concurso.infra.role.Role;
import br.co.progresso.concurso.infra.role.RoleRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerIntegrationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private JwtService jwtService;

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private RoleRepository roleRepository;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void testLoginSuccess() throws Exception {
		// Dados de entrada
		String username = "admin";
		String password = "password";
		LoginRequest loginRequest = new LoginRequest(1L,username, password);

		// Mock do retorno do AuthenticationManager
		Authentication authentication = mock(Authentication.class);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenReturn(authentication);

		// Mock do serviço JWT
		String mockToken = "mock-jwt-token";
		
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		
		when(jwtService.generateToken(username, 1L, 
				roles.stream().map(Role::getName).collect(Collectors.toSet()))).thenReturn(mockToken);

		// Execução do teste
		ResultActions result = mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest)));

		// Verificações
		result.andExpect(status().isOk()).andExpect(jsonPath("$.token").value(mockToken));

		// Verifica interações
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
		verify(jwtService, times(1)).generateToken(username, 1L , 
				roles.stream().map(Role::getName).collect(Collectors.toSet()));
	}

}
