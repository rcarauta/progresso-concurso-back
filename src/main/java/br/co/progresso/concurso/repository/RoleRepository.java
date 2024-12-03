package br.co.progresso.concurso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.co.progresso.concurso.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);

}
