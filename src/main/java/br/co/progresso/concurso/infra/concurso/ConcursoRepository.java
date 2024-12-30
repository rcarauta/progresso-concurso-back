package br.co.progresso.concurso.infra.concurso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.co.progresso.concurso.application.concurso.ConcursoRequest;

import java.util.List;

@Repository
public interface ConcursoRepository extends JpaRepository<Concurso, Long> {

    List<Concurso> findByUserId(Long userId);
    
    @Query("SELECT new br.co.progresso.concurso.application.concurso.ConcursoRequest( " +
            "c.id, " +
            "c.nome, " +
            "CAST(COALESCE(SUM(cdm.porcentagem) / COUNT(cdm.id), 0) AS float), " +
            "c.user.id) "+
            "FROM Concurso c " +
            "JOIN c.contestDisciplinaMaterias cdm " +
            "WHERE c.user.id = :userId " +
            "GROUP BY c.id, c.nome, c.user.id")
     List<ConcursoRequest> findConcursoRequestsByUserId(@Param("userId") Long userId);

}
