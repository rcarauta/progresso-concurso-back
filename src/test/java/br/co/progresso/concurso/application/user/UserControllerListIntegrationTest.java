package br.co.progresso.concurso.application.user;

import br.co.progresso.concurso.application.authentication.JwtService;
import br.co.progresso.concurso.infra.role.Role;
import br.co.progresso.concurso.infra.role.RoleRepository;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerListIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void verifyListAllUsers() throws Exception {
        List<UserRequest> listUserRequest = Arrays.asList(
                new UserRequest(1L, "Teste", "1234", true),
                new UserRequest(2L, "Outro Teste", "123456", false)
        );

        Mockito.when(userService.listarTodosUsuarios()).thenReturn(listUserRequest);

		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L,
				roles.stream().map(Role::getName).collect(Collectors.toSet()));

        mockMvc.perform(get("/user/list") .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(listUserRequest.size()))
                .andExpect(jsonPath("$[0].id").value(listUserRequest.get(0).getId()))
                .andExpect(jsonPath("$[0].username").value(listUserRequest.get(0).getUsername()));
    }

}
