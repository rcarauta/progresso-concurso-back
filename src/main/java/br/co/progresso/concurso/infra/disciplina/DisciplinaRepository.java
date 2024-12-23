package br.co.progresso.concurso.infra.disciplina;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

	Disciplina findByNome(String nome);
	
	@Query("SELECT d FROM Disciplina d JOIN d.concursos c WHERE c.id = :concursoId")
	List<Disciplina> findBayIdConcurso(Long concursoId);
	
	@Query("SELECT d FROM Disciplina d \n"
			+ "WHERE NOT EXISTS ( \n"
			+ "  SELECT 1 FROM Concurso c \n"
			+ "  WHERE c.id = :concursoId AND c MEMBER OF d.concursos \n"
			+ ")")
	List<Disciplina> findByNotIdConcurso(Long concursoId);
	
}
