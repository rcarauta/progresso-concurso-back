package br.co.progresso.concurso.infra.concurso_disciplina_materia;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcursoDisciplinaMateriaRepository extends JpaRepository<ConcursoDisciplinaMateria, ConcursoDisciplinaMateriaId> {
    
	List<ConcursoDisciplinaMateria> findByIdConcursoIdAndIdDisciplinaId(Long concursoId, Long disciplinaId);
	
}