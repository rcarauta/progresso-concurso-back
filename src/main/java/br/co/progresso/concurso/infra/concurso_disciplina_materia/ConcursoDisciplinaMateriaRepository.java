package br.co.progresso.concurso.infra.concurso_disciplina_materia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.co.progresso.concurso.infra.disciplina_materia.DisciplinaMateriaDto;

public interface ConcursoDisciplinaMateriaRepository extends JpaRepository<ConcursoDisciplinaMateria, ConcursoDisciplinaMateriaId> {
    
	List<ConcursoDisciplinaMateria> findByIdConcursoIdAndIdDisciplinaId(Long concursoId, Long disciplinaId);
	
	List<ConcursoDisciplinaMateria> findByConcursoId(Long concursoId);
	
	@Query("""
		    SELECT new br.co.progresso.concurso.infra.disciplina_materia.DisciplinaMateriaDto(
		        d.nome, 
		        COALESCE(SUM(cdm.totalQuestoes), 0L), 
		        COALESCE(SUM(cdm.questoesAcertadas), 0L),
		        d.categoria
		    )
		    FROM ConcursoDisciplinaMateria cdm
		    JOIN cdm.disciplina d
		    JOIN cdm.concurso c
		    WHERE c.id = :concursoId
		    GROUP BY d.nome, d.categoria
		    ORDER BY d.nome
		""")
	List<DisciplinaMateriaDto> findDisciplinaResumo(@Param("concursoId") Long concursoId);

	
}