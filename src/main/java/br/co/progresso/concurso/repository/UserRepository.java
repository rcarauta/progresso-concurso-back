package br.co.progresso.concurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.co.progresso.concurso.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
}
