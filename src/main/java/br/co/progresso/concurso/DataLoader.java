package br.co.progresso.concurso;

import org.springframework.stereotype.Component;

import br.co.progresso.concurso.model.Role;
import br.co.progresso.concurso.model.User;
import br.co.progresso.concurso.repository.RoleRepository;
import br.co.progresso.concurso.repository.UserRepository;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin123").isEmpty()) {
        	
        	String roleName = "ROLE_ADMIN";
            Role role = roleRepository.findByName(roleName)
                    .orElseGet(() -> {
                        Role newRole = new Role();
                        newRole.setName(roleName);
                        return roleRepository.save(newRole);
                    });
        	
            User user = new User();
            user.setUsername("admin123");
            user.setPassword(passwordEncoder.encode("password"));// Codifica a senha
            user.setEnabled(true);
            user.setRoles(Set.of(role));
            userRepository.save(user);
            System.out.println(passwordEncoder.encode("password"));
        }
    }
}
