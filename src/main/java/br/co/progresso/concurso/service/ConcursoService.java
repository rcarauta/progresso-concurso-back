package br.co.progresso.concurso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.convert.ConcursoConverter;
import br.co.progresso.concurso.model.Concurso;
import br.co.progresso.concurso.repository.ConcursoRepository;
import br.co.progresso.concurso.request.ConcursoRequest;

@Service
public class ConcursoService {
	
	@Autowired
	private ConcursoRepository concursoRepostiory;
	
	@Autowired
	private ConcursoConverter concursoConverter;
	
	@Transactional
	public ConcursoRequest salvar(ConcursoRequest concursoRequest) {
		Concurso concurso = concursoConverter.concursoRequestToConcurso(concursoRequest);
		concurso = concursoRepostiory.save(concurso);
		return concursoConverter.concursoToConcursoRequest(concurso); 
	}

}
