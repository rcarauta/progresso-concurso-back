package br.co.progresso.concurso.application.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String passwordEncoder(String password) {
        return passwordEncoder.encode(password);
    }
}
