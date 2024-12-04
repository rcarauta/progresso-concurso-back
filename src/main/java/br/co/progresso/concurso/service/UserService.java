package br.co.progresso.concurso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.co.progresso.concurso.model.User;
import br.co.progresso.concurso.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public User pegarUsuarioPeloUsername(String username) {
		return userRepository.findByUsername(username).get();
	}
	
}
