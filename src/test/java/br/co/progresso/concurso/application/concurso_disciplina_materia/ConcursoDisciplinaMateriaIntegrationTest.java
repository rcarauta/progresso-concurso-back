package br.co.progresso.concurso.application.concurso_disciplina_materia;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.co.progresso.concurso.application.authentication.JwtService;
import br.co.progresso.concurso.application.disciplina.CategoriaDisciplina;
import br.co.progresso.concurso.application.util.DataConverter;
import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import br.co.progresso.concurso.infra.materia.Materia;
import br.co.progresso.concurso.infra.materia.MateriaRepository;
import br.co.progresso.concurso.infra.role.Role;
import br.co.progresso.concurso.infra.role.RoleRepository;
import br.co.progresso.concurso.infra.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ConcursoDisciplinaMateriaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConcursoRepository concursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private MateriaRepository materiaRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private DataConverter converter;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        //concursoDisciplinaMateriaRepository.deleteAll();
        //disciplinaRepository.deleteAll();
       // materiaRepository.deleteAll();
       // concursoRepository.deleteAll();
    }

    @Test
    void testAssociarMateriaAoConcursoDisciplina() throws Exception {
    	 int randomNumber = (int)(Math.random() * 1_000_000_000) + 1;
         String nomeDisciplina = "Matemárica"+randomNumber;
    	
    	 Concurso concurso = new Concurso();
         concurso.setNome("Concurso Teste");
         concurso.setDataProvaDate(converter.converterStringParaDate("20230-12-10"));
         concurso.setPercentualEstudadoFloat(10.7F);
         concurso.setUser(userRepository.findById(1L).get());
         concurso = concursoRepository.save(concurso);
         
         Disciplina disciplina = new Disciplina();
         disciplina.setNome(nomeDisciplina);
         disciplina.setCiclos(2);
         disciplina.setCategoria(CategoriaDisciplina.BASICA);
         disciplina.setPorcentagem(10.5F);
         disciplina = disciplinaRepository.save(disciplina);

         Materia materia = new Materia();
         materia.setNome("Matéria Teste");
         materia = materiaRepository.save(materia);
    	
        Long concursoId = concurso.getId();
        Long disciplinaId = disciplina.getId();
        Long materiaId = materia.getId();
       
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L,
				roles.stream().map(Role::getName).collect(Collectors.toSet()));

        String materiaRequestJson = """
                {
                    "id": %d,
                    "nome": "Matéria Teste"
                }
                """.formatted(materiaId);

        mockMvc.perform(post("/concurso_disciplina_materia/%d/%d/associar".formatted(concursoId, disciplinaId))
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(materiaRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.materiaId", is(materiaId.intValue())))
                .andExpect(jsonPath("$.disciplinaId", is(disciplinaId.intValue())))
                .andExpect(jsonPath("$.concursoId", is(concursoId.intValue())));
    }
    
    @Test
    void listarTodasAsMateriasNoaAssociadaAoConcursoEDisciplina() throws Exception {
    	 int randomNumber = (int)(Math.random() * 1_000_000_000) + 1;
         String nomeDisciplina = "Matemárica"+randomNumber;
    	
    	 Concurso concurso = new Concurso();
         concurso.setNome("Concurso Teste");
         concurso.setDataProvaDate(converter.converterStringParaDate("20230-12-10"));
         concurso.setPercentualEstudadoFloat(10.7F);
         concurso.setUser(userRepository.findById(1L).get());
         concurso = concursoRepository.save(concurso);
         
         Disciplina disciplina = new Disciplina();
         disciplina.setNome(nomeDisciplina);
         disciplina.setCiclos(2);
         disciplina.setCategoria(CategoriaDisciplina.BASICA);
         disciplina.setPorcentagem(10.5F);
         disciplina = disciplinaRepository.save(disciplina);

         Materia materia = new Materia();
         materia.setNome("Matéria Teste");
         materia = materiaRepository.save(materia);
    	
        Long concursoId = concurso.getId();
        Long disciplinaId = disciplina.getId();
        Long materiaId = materia.getId();
        
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L,
				roles.stream().map(Role::getName).collect(Collectors.toSet()));

        String materiaRequestJson = """
                {
                    "id": %d,
                    "nome": "Matéria Teste"
                }
                """.formatted(materiaId);

        mockMvc.perform(post("/concurso_disciplina_materia/%d/%d/associar".formatted(concursoId, disciplinaId))
        		.header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(materiaRequestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.materiaId", is(materiaId.intValue())))
                .andExpect(jsonPath("$.disciplinaId", is(disciplinaId.intValue())))
                .andExpect(jsonPath("$.concursoId", is(concursoId.intValue())));
        
        mockMvc.perform(get("/materia/%d/%d/list".formatted(concurso.getId(), disciplina.getId()))
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
