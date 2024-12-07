package br.co.progresso.concurso.application.concurso;

import java.util.ArrayList;
import java.util.List;

import br.co.progresso.concurso.application.util.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.user.UserRepository;

@Component
public class ConcursoConverter {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DataConverter dataConverter;

	public Concurso concursoRequestToConcurso(ConcursoRequest request) {
		Concurso concurso = new Concurso();
		concurso.setId(request.getId());
		concurso.setNome(request.getNome());
		concurso.setDataProvaDate(dataConverter.converterStringParaDate(request.getDataProvaDate()));
		concurso.setPercentualEstudadoFloat(request.getPercentualEstudadoFloat());
		concurso.setUser(getUsuerPotrId(request.getUserId()));

		return concurso;
	}

	public ConcursoRequest concursoToConcursoRequest(Concurso concurso) {
		ConcursoRequest request = new ConcursoRequest();
		request.setId(concurso.getId());
		request.setDataProvaDate(dataConverter.converterDateParaString(concurso.getDataProvaDate()));
		request.setNome(concurso.getNome());
		request.setPercentualEstudadoFloat(concurso.getPercentualEstudadoFloat());
		request.setUserId(verifyHasConcursoId(concurso.getUser()));

		return request;
	}

	public List<ConcursoRequest> listConcursoToListConcursoRequest(List<Concurso> listConcurso) {
		List<ConcursoRequest> request = new ArrayList<>();
		listConcurso.forEach(concurso -> {
			request.add(concursoToConcursoRequest(concurso));
		});
		return request;
	}

	private Long verifyHasConcursoId(User user) {
		if (user == null) {
			return null;
		}
		return user.getId();
	}

	public User getUsuerPotrId(Long userId) {
		return userRepository.findById(userId).get();
	}

}
