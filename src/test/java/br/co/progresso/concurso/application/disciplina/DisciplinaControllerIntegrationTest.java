package br.co.progresso.concurso.application.disciplina;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import br.co.progresso.concurso.application.authentication.JwtService;
import br.co.progresso.concurso.application.util.DataConverter;
import br.co.progresso.concurso.infra.concurso.Concurso;
import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaId;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateriaRepository;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import br.co.progresso.concurso.infra.disciplina.DisciplinaRepository;
import br.co.progresso.concurso.infra.materia.Materia;
import br.co.progresso.concurso.infra.materia.MateriaRepository;
import br.co.progresso.concurso.infra.role.Role;
import br.co.progresso.concurso.infra.role.RoleRepository;
import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.user.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DisciplinaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConcursoRepository concursoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ConcursoDisciplinaMateriaRepository concursoDisciplinaMateriaRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private DataConverter converter;
    
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testTotalPorcentagemDisciplina() throws Exception {	
    	int randomNumber = (int)(Math.random() * 1_000_000_000) + 1;
    	
    	User user = new User();
    	user.setUsername("rcarauta");
    	user.setPassword("senha");
    	user.setEnabled(true);
    	user.setRoles(null);
    	userRepository.save(user);
    	
        Concurso concurso = new Concurso();
        concurso.setNome("Concurso 1");
        concurso.setDataProvaDate(converter.converterStringParaDate("2030-10-10"));
        concurso.setPercentualEstudadoFloat(1f);
        concurso.setUser(user); 
        concurso = concursoRepository.save(concurso);

        
        
        Disciplina disciplina1 = new Disciplina();
        disciplina1.setNome("Matemática "+randomNumber);
        disciplina1.setCategoria(CategoriaDisciplina.BASICA);
        disciplina1.setCiclos(3);
        disciplinaRepository.save(disciplina1);

        Disciplina disciplina2 = new Disciplina();
        disciplina2.setNome("Português "+randomNumber);
        disciplina2.setCategoria(CategoriaDisciplina.BASICA);
        disciplina2.setCiclos(2);
        disciplinaRepository.save(disciplina2);

        Materia materia1 = new Materia();
        materia1.setNome("Álgebra "+randomNumber);
        materiaRepository.save(materia1);

        Materia materia2 = new Materia();
        materia2.setNome("Geometria "+randomNumber);
        materiaRepository.save(materia2);

        Materia materia3 = new Materia();
        materia3.setNome("Gramática "+randomNumber);
        materiaRepository.save(materia3);

        Materia materia4 = new Materia();
        materia4.setNome("Interpretação de Textos "+randomNumber);
        materiaRepository.save(materia4);

        salvarConcursoDisciplinaMateria(concurso, disciplina1, materia1, 30f);
        salvarConcursoDisciplinaMateria(concurso, disciplina1, materia2, 20f);
        salvarConcursoDisciplinaMateria(concurso, disciplina2, materia3, 40f);
        salvarConcursoDisciplinaMateria(concurso, disciplina2, materia4, 60f);
        
		Role role = roleRepository.findByName("ROLE_ADMIN").get();
		
		Set<Role> roles = new HashSet<>();
		roles.add(role);

		// Gerar um token JWT válido
		String token = jwtService.generateToken("admin", 1L,
				roles.stream().map(Role::getName).collect(Collectors.toSet()));

        mockMvc.perform(get("/disciplina/" + concurso.getId() + "/porcentagem_disciplina")
        		.header("Authorization", "Bearer " + token)
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)) 
                .andExpect(jsonPath("$[0].nome").value("Matemática "+randomNumber))
                .andExpect(jsonPath("$[0].porcentagem").value(25.0)) 
                .andExpect(jsonPath("$[1].nome").value("Português "+randomNumber))
                .andExpect(jsonPath("$[1].porcentagem").value(50.0));
    }

    private void salvarConcursoDisciplinaMateria(Concurso concurso, Disciplina disciplina, Materia materia, Float porcentagem) {
        ConcursoDisciplinaMateria cdm = new ConcursoDisciplinaMateria();
        cdm.setId(new ConcursoDisciplinaMateriaId(concurso.getId(), disciplina.getId(), materia.getId()));
        cdm.setConcurso(concurso);
        cdm.setDisciplina(disciplina);
        cdm.setMateria(materia);
        cdm.setPorcentagem(porcentagem);
        concursoDisciplinaMateriaRepository.save(cdm);
    }
}
