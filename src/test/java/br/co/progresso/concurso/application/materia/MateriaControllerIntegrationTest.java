package br.co.progresso.concurso.application.materia;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.co.progresso.concurso.application.authentication.JwtService;
import br.co.progresso.concurso.infra.role.Role;
import br.co.progresso.concurso.infra.role.RoleRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
class MateriaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void cadastrarMateria_DeveCriarMateriaERetornar201() throws Exception {

        MateriaRequest request = new MateriaRequest();
        request.setNome("Emprego de tempos e modos verbais");


        String requestJson = objectMapper.writeValueAsString(request);
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L,
				roles.stream().map(Role::getName).collect(Collectors.toSet()));

        mockMvc.perform(post("/materia")
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Emprego de tempos e modos verbais"));
        
    }
    
    @Test
    void atualizarMateriaComSucesso() throws Exception {
        MateriaRequest novaMateria = new MateriaRequest();
        novaMateria.setNome("Matemática");

        String materiaJson = objectMapper.writeValueAsString(novaMateria);
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L,
				roles.stream().map(Role::getName).collect(Collectors.toSet()));

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

        String materiaAtualizadaJson = objectMapper.writeValueAsString(materiaSalva);

        mockMvc.perform(put("/materia")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .content(materiaAtualizadaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matemática Avançada"));
    }
    
}
