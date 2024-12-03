package br.co.progresso.concurso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import br.co.progresso.concurso.request.AuthResponse;
import br.co.progresso.concurso.request.LoginRequest;
import br.co.progresso.concurso.service.JwtService;

@RestController
@RequestMapping("/login")
public class AuthController {

	@Autowired
	private JwtService jwtService;	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
	    );

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    // Gera o token JWT
	    String token = jwtService.generateToken(loginRequest.getUsername());

	    return ResponseEntity.ok(new AuthResponse(token));
	}

}
