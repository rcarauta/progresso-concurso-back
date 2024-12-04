package br.co.progresso.concurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.co.progresso.concurso.model.Concurso;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConcursoRepository extends JpaRepository<Concurso, Long> {

    List<Concurso> findByUserId(Long userId);

}
