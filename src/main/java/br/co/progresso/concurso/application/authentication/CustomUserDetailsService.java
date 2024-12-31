package br.co.progresso.concurso.application.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.user.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final JwtService jwtService; 

	public CustomUserDetailsService(UserRepository userRepository, JwtService jwtService) {
		this.userRepository = userRepository;
		this.jwtService = jwtService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

		var authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.isEnabled(), true, true, true, authorities);
	}

	public UserDetails loadUserWithTokenRoles(String token) {
		String username = jwtService.extractUsername(token);
		Set<String> roles = jwtService.extractRoles(token);

		UserDetails userDetails = loadUserByUsername(username);

		Set<SimpleGrantedAuthority> tokenAuthorities = roles.stream().map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());

		Collection<GrantedAuthority> combinedAuthorities = userDetails.getAuthorities().stream()
			    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority())) 
			    .collect(Collectors.toList()); 
 
		combinedAuthorities.addAll(tokenAuthorities); 

		return new CustomUserDetails(userDetails, combinedAuthorities);
	}


	public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {

		private final Collection<GrantedAuthority> tokenAuthorities;

		public CustomUserDetails(UserDetails userDetails, Collection<GrantedAuthority> tokenAuthorities) {
			super(userDetails.getUsername(), userDetails.getPassword(), userDetails.isEnabled(),
					userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(),
					userDetails.isAccountNonLocked(), userDetails.getAuthorities());
			this.tokenAuthorities = tokenAuthorities;
		}

		@Override
		public Collection<GrantedAuthority> getAuthorities() {
			return tokenAuthorities; 
		}
	}
}
