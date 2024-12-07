package br.co.progresso.concurso.application.user;

import br.co.progresso.concurso.infra.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {

    public User userRequestToUser(UserRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEnabled(request.isEnabled());
        return user;
    }

    public UserRequest usertoUserRequest(User user) {
        UserRequest request = new UserRequest();
        request.setId(user.getId());
        request.setUsername(user.getUsername());
        request.setPassword(user.getPassword());
        request.setEnabled(user.isEnabled());
        return request;
    }

    public List<UserRequest> listUserToUserRequest(List<User> listUser) {
        List<UserRequest> listaRequest = new ArrayList<>();
        listUser.forEach(user -> {
            listaRequest.add(usertoUserRequest(user));
        });
        return listaRequest;
    }
}
