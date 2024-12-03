package br.co.progresso.concurso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.co.progresso.concurso.model.Concurso;

public interface ConcursoRepository extends JpaRepository<Concurso, Long> {

}
