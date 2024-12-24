package br.co.progresso.concurso.infra.concurso_disciplina;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.co.progresso.concurso.infra.disciplina.Disciplina;

public interface ConcursoDisciplinaRepository extends JpaRepository<ConcursoDisciplina, ConcursoDisciplinaId> {

	 @Query("SELECT cd FROM ConcursoDisciplina cd WHERE cd.id.contestId = :contestId AND cd.id.disciplinaId = :disciplinaId")
	 Optional<ConcursoDisciplina> findByContestIdAndDisciplinaId(@Param("contestId") Long contestId, @Param("disciplinaId") Long disciplinaId);
	
	 @Query("SELECT cd.disciplina " +
	           "FROM ConcursoDisciplina cd " +
	           "WHERE cd.id.contestId = :contestId AND cd.ordem IS NOT NULL " +
	           "ORDER BY cd.ordem ASC")
	    List<Disciplina> findDisciplinasByContestIdWithOrder(Long contestId);
	 
}
