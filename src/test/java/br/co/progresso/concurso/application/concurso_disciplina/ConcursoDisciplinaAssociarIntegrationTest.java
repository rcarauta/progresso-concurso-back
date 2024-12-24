package br.co.progresso.concurso.application.concurso_disciplina;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.co.progresso.concurso.application.authentication.JwtService;
import br.co.progresso.concurso.application.concurso.ConcursoRequest;
import br.co.progresso.concurso.application.disciplina.CategoriaDisciplina;
import br.co.progresso.concurso.application.disciplina.DisciplinaRequest;
import br.co.progresso.concurso.application.util.DataConverter;
import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import br.co.progresso.concurso.infra.materia.Materia;
import br.co.progresso.concurso.infra.materia.MateriaRepository;
import br.co.progresso.concurso.infra.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ConcursoDisciplinaAssociarIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
    private DataConverter converter;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ConcursoRepository concursoRepository;

	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private MateriaRepository materiaRepository;

	@Test
	public void testAssociarDisciplinasAoConcurso() throws Exception {

		ConcursoRequest concursoRequest = new ConcursoRequest();
		concursoRequest.setNome("Concurso Público 2024");
		concursoRequest.setDataProvaDate("2024-12-31");
		concursoRequest.setPercentualEstudadoFloat(0.1f);
		concursoRequest.setUserId(1L);

		String token = jwtService.generateToken("admin", 1L);

		String concursoResponse = mockMvc
				.perform(post("/concurso").header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(concursoRequest)))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		ConcursoRequest concursoSalvo = objectMapper.readValue(concursoResponse, ConcursoRequest.class);

		assertNotNull(concursoSalvo, "Concurso salvo deve ser diferente de null");
		assertNotNull(concursoSalvo.getId(), "ID do concurso salvo deve ser diferente de null");

		DisciplinaRequest disciplinaRequest1 = new DisciplinaRequest();
		int randomNumber = (int) (Math.random() * 1_000_000_000) + 1;
		String matematica = "Matemática" + randomNumber;
		String portugues = "Português" + randomNumber;

		disciplinaRequest1.setNome(matematica);
		disciplinaRequest1.setPorcentagem(0.0f);
		disciplinaRequest1.setCiclos(2);

		String disciplinaResponse1 = mockMvc
				.perform(post("/disciplina").header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(disciplinaRequest1)))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		DisciplinaRequest disciplinaSalva1 = objectMapper.readValue(disciplinaResponse1, DisciplinaRequest.class);

		DisciplinaRequest disciplinaRequest2 = new DisciplinaRequest();
		disciplinaRequest2.setNome(portugues);
		disciplinaRequest2.setPorcentagem(0.0f);
		disciplinaRequest2.setCiclos(1);

		String disciplinaResponse2 = mockMvc
				.perform(post("/disciplina").header("Authorization", "Bearer " + token)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(disciplinaRequest2)))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		DisciplinaRequest disciplinaSalva2 = objectMapper.readValue(disciplinaResponse2, DisciplinaRequest.class);

		assertNotNull(disciplinaSalva1, "DisciplinaSalva1 deve ser diferente de null");
		assertNotNull(disciplinaSalva1.getId(), "ID da DisciplinaSalva1 deve ser diferente de null");

		assertNotNull(disciplinaSalva2, "DisciplinaSalva2 deve ser diferente de null");
		assertNotNull(disciplinaSalva2.getId(), "ID da DisciplinaSalva2 deve ser diferente de null");

		List<Long> disciplinaIds = Arrays.asList(disciplinaSalva1.getId(), disciplinaSalva2.getId());

		String associacaoResponse = mockMvc
				.perform(post("/concurso_disciplina/" + concursoSalvo.getId() + "/associar")
						.header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(disciplinaIds)))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		ConcursoRequest concursoComDisciplinas = objectMapper.readValue(associacaoResponse, ConcursoRequest.class);

		assertNotNull(concursoComDisciplinas, "O concurso com disciplinas não deve ser null");
		assertNotNull(concursoComDisciplinas.getListaDisciplinaEntity(), "A lista de disciplinas não deve ser null");
		assertEquals(1, concursoComDisciplinas.getListaDisciplinaEntity().size(),
				"A quantidade de disciplinas deve ser 2");

		List<String> nomesDisciplinas = concursoComDisciplinas.getListaDisciplinaEntity().stream()
				.map(Disciplina::getNome).toList();

		List<String> nomesEsperados = List.of(matematica);
		assertTrue(nomesDisciplinas.containsAll(nomesEsperados) && nomesEsperados.containsAll(nomesDisciplinas));

		String listaDisciplina = mockMvc
				.perform(get("/concurso_disciplina/" + concursoSalvo.getId() + "/todas_disciplinas")
						.header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		ConcursoRequest concursoComListaDisciplinas = objectMapper.readValue(listaDisciplina, ConcursoRequest.class);

		assertNotNull(concursoComListaDisciplinas);
	}

	@Test
	void testRecuperarMateriaAoConcursoDisciplina() throws Exception {
		int randomNumber = (int) (Math.random() * 1_000_000_000) + 1;
		String nomeDisciplina = "Matemática" + randomNumber;

		Concurso concurso = new Concurso();
		concurso.setNome("Concurso Recuperar Teste");
		concurso.setDataProvaDate(converter.converterStringParaDate("2030-12-10"));
		concurso.setPercentualEstudadoFloat(15.7F);
		concurso.setUser(userRepository.findById(1L).get());
		concurso = concursoRepository.save(concurso);

		// Criação de Disciplina
		Disciplina disciplina = new Disciplina();
		disciplina.setNome(nomeDisciplina);
		disciplina.setCiclos(3);
		disciplina.setCategoria(CategoriaDisciplina.ESPECIFICA);
		disciplina.setPorcentagem(20.0F);
		disciplina = disciplinaRepository.save(disciplina);

		// Criação de Matéria
		Materia materia = new Materia();
		materia.setNome("Matéria Recuperar Teste");
		materia = materiaRepository.save(materia);

		// Associação
		Long concursoId = concurso.getId();
		Long disciplinaId = disciplina.getId();
		Long materiaId = materia.getId();
		String token = jwtService.generateToken("admin", 1L);

		String materiaRequestJson = """
				{
				    "id": %d,
				    "nome": "Matéria Recuperar Teste"
				}
				""".formatted(materiaId);

		mockMvc.perform(post("/concurso_disciplina_materia/%d/%d/associar".formatted(concursoId, disciplinaId))
		        .header("Authorization", "Bearer " + token)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(materiaRequestJson))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.materiaId", org.hamcrest.Matchers.is(materiaId.intValue())))
		        .andExpect(jsonPath("$.disciplinaId", org.hamcrest.Matchers.is(disciplinaId.intValue())))
		        .andExpect(jsonPath("$.concursoId", org.hamcrest.Matchers.is(concursoId.intValue())));

		// Recuperação
		mockMvc.perform(get("/concurso_disciplina_materia/%d/%d/recuperar".formatted(concursoId, disciplinaId))
		        .header("Authorization", "Bearer " + token)
		        .contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[0].id", org.hamcrest.Matchers.is(materiaId.intValue())))
		        .andExpect(jsonPath("$[0].nome", org.hamcrest.Matchers.is("Matéria Recuperar Teste")));
		
	}	
		
}
