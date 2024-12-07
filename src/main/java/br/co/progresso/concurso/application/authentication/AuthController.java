package br.co.progresso.concurso.application.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.application.user.UserService;

@RestController
@RequestMapping("/login")
public class AuthController {

	@Autowired
	private JwtService jwtService;	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
	    );

	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    
	    User user = userService.pegarUsuarioPeloUsername(loginRequest.getUsername());  
	    
	    // Gera o token JWT
	    String token = jwtService.generateToken(loginRequest.getUsername(), user.getId());

	    return ResponseEntity.ok(new AuthResponse(token));
	}

}
