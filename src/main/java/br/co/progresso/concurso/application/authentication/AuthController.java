package br.co.progresso.concurso.application.authentication;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import br.co.progresso.concurso.infra.role.Role;
import br.co.progresso.concurso.infra.role.RoleRepository;
import br.co.progresso.concurso.infra.user.User;
import jakarta.servlet.http.HttpServletRequest;
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
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = userService.pegarUsuarioPeloUsername(loginRequest.getUsername());
		
		// Gera o token JWT
		String token = jwtService.generateToken(loginRequest.getUsername(), user.getId(),
				 user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));

		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/generate-token/{username}")
	public ResponseEntity<?> generateTokenForUser(@PathVariable String username,  HttpServletRequest request) {
		User user = userService.pegarUsuarioPeloUsername(username);
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		
		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
		}
		
		
		String token = jwtService.generateToken(user.getUsername(), user.getId(),
				 user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
		
		 authenticateUser(user, token, request);
		
		return ResponseEntity.ok(new AuthResponse(token));
	}
	
	private void authenticateUser(User user, String token, HttpServletRequest request) throws UsernameNotFoundException {
	    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
	    
	    var authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities() 
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);
	
        jwtService.isTokenValid(token, userDetails); 

	}

}
