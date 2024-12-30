package br.co.progresso.concurso.infra.disciplina;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.co.progresso.concurso.application.disciplina.DisciplinaRequest;

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
	
	@Query("SELECT new br.co.progresso.concurso.application.disciplina.DisciplinaRequest( " +
		       "d.id, " +
		       "d.nome, " +
		       "CAST(COALESCE(SUM(cdm.porcentagem) / COUNT(cdm.id), 0) AS float), " +
		       "d.categoria, " +
		       "d.ciclos) " +
		       "FROM Disciplina d " +
		       "JOIN d.contestDisciplinaMaterias cdm " +
		       "WHERE cdm.concurso.id = :idConcurso " +
		       "GROUP BY d.id, d.nome, d.categoria, d.ciclos")
	List<DisciplinaRequest> findDisciplinasWithAverageProgressByConcurso(@Param("idConcurso") Long idConcurso);

	
}
