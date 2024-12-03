package br.co.progresso.concurso.convert;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.co.progresso.concurso.model.Concurso;
import br.co.progresso.concurso.model.User;
import br.co.progresso.concurso.repository.UserRepository;
import br.co.progresso.concurso.request.ConcursoRequest;

@Component
public class ConcursoConverter {

	@Autowired
	private UserRepository userRepository;

	public Concurso concursoRequestToConcurso(ConcursoRequest request) {
		Concurso concurso = new Concurso();
		concurso.setId(request.getId());
		concurso.setNome(request.getNome());
		concurso.setDataProvaDate(converterStringParaDate(request.getDataProvaDate()));
		concurso.setPercentualEstudadoFloat(request.getPercentualEstudadoFloat());
		concurso.setUser(getUsuerPotrId(request.getUserId()));

		return concurso;
	}

	public ConcursoRequest concursoToConcursoRequest(Concurso concurso) {
		ConcursoRequest request = new ConcursoRequest();
		request.setId(concurso.getId());
		request.setDataProvaDate(converterDateParaString(concurso.getDataProvaDate()));
		request.setNome(concurso.getNome());
		request.setPercentualEstudadoFloat(concurso.getPercentualEstudadoFloat());
		request.setUserId(verifyHasConcursoId(concurso.getUser()));

		return request;
	}

	private Long verifyHasConcursoId(User user) {
		if (user == null) {
			return null;
		}
		return user.getId();
	}

	private User getUsuerPotrId(Long userId) {
		return userRepository.findById(userId).get();
	}

	private Date converterStringParaDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return formatter.parse(date);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String converterDateParaString(Date date) {
	  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
      return formatter.format(date);
	}

}
