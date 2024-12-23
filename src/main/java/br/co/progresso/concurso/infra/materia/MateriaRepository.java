package br.co.progresso.concurso.infra.materia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MateriaRepository extends JpaRepository<Materia, Long>  {

	   @Query("""
		        SELECT m 
		        FROM Materia m 
		        WHERE NOT EXISTS (
		            SELECT 1 
		            FROM ConcursoDisciplinaMateria cdm 
		            WHERE cdm.id.materiaId = m.id 
		              AND cdm.id.concursoId = :concursoId 
		              AND cdm.id.disciplinaId = :disciplinaId
		        )
		    """)
	List<Materia> listarTodasAsMaterias(Long concursoId, Long disciplinaId);
	   
	   @Query("""
		        SELECT m 
		        FROM Materia m 
		        WHERE EXISTS (
		            SELECT 1 
		            FROM ConcursoDisciplinaMateria cdm 
		            WHERE cdm.id.materiaId = m.id 
		              AND cdm.id.concursoId = :concursoId 
		              AND cdm.id.disciplinaId = :disciplinaId
		        )
		    """)
	List<Materia> listarTodasAsMateriasAssociadas(Long concursoId, Long disciplinaId);
	
}
