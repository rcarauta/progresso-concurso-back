package br.co.progresso.concurso.application.user;

import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.user.UserRepository;
import br.co.progresso.concurso.application.authentication.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerSaveIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Test
    public void deveSalvarUsuarioComSucesso() throws Exception {
        UserRequest userRequest = new UserRequest();
        int randomNumber = (int)(Math.random() * 1_000_000_000) + 1;
        String username = "Teste Usuário"+randomNumber;
        userRequest.setUsername(username);
        userRequest.setPassword("senha123");
        userRequest.setEnabled(true);

        String token = jwtService.generateToken("admin", 1L);

        mockMvc.perform(post("/user")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.enabled").value(true));

        Optional<User> usuarioSalvo = userRepository.findByUsername(userRequest.getUsername());
        assertTrue(usuarioSalvo.isPresent());
        assertEquals(username, usuarioSalvo.get().getUsername());
        assertTrue(usuarioSalvo.get().isEnabled());

        UserRequest usuarioSalvoRequest = userService.buscarUsuario(usuarioSalvo.get().getId());
        assertNotNull(usuarioSalvoRequest);
        assertEquals(username, usuarioSalvoRequest.getUsername());
        assertTrue(usuarioSalvo.get().isEnabled());
    }

}
