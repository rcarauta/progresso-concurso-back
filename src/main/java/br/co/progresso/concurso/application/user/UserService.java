package br.co.progresso.concurso.application.user;

import br.co.progresso.concurso.application.UsuarioNaoEncontradoException;
import br.co.progresso.concurso.application.util.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.user.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private Encoder encoder;
	
	public User pegarUsuarioPeloUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	@Transactional
	public UserRequest salvarUsuario(UserRequest userRequest) {
		User user = userConverter.userRequestToUser(userRequest);
		user.setPassword(encoder.passwordEncoder(user.getPassword()));
		User userSalvo = userRepository.save(user);
		return userConverter.usertoUserRequest(userSalvo);
	}

	public UserRequest buscarUsuario(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
		return userConverter.usertoUserRequest(user);
	}

	public List<UserRequest> listarTodosUsuarios() {
		List<User> listaUser = userRepository.findAll();
		return userConverter.listUserToUserRequest(listaUser);
	}
}