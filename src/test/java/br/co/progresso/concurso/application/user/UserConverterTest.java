package br.co.progresso.concurso.application.user;

import br.co.progresso.concurso.infra.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserConverterTest {

    private UserRequest request;
    private User user;

    @BeforeEach
    public void setup() {
        request = new UserRequest();
        request.setId(1L);
        request.setUsername("john_doe");
        request.setPassword("password123");
        request.setEnabled(true);

        user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setPassword("password123");
        user.setEnabled(true);
    }


    @Test
    public void testUserRequestToUser() {
        UserConverter converter = new UserConverter();
        User user = converter.userRequestToUser(request);

        assertNotNull(user);
        assertEquals(request.getId(), user.getId());
        assertEquals(request.getUsername(), user.getUsername());
        assertEquals(request.getPassword(), user.getPassword());
        assertTrue(user.isEnabled());
    }

    @Test
    public void testUserToUserRequest() {
        UserConverter converter = new UserConverter();
        UserRequest request = converter.usertoUserRequest(user);

        assertNotNull(request);
        assertEquals(user.getId(), request.getId());
        assertEquals(user.getUsername(), request.getUsername());
        assertEquals(user.getPassword(), request.getPassword());
        assertTrue(request.isEnabled());
    }

}
