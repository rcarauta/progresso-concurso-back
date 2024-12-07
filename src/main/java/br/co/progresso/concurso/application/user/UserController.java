package br.co.progresso.concurso.application.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserRequest> salvarUsuario(@RequestBody UserRequest userRequest) {
        UserRequest usuarioSalvo = userService.salvarUsuario(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserRequest>> listarTodosUsuarios() {
        List<UserRequest> userRequest = userService.listarTodosUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(userRequest);
    }

}
