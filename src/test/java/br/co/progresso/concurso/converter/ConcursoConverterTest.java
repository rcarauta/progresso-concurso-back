package br.co.progresso.concurso.converter;

import br.co.progresso.concurso.convert.ConcursoConverter;
import br.co.progresso.concurso.convert.DataConverter;
import br.co.progresso.concurso.model.Concurso;
import br.co.progresso.concurso.model.User;
import br.co.progresso.concurso.repository.UserRepository;
import br.co.progresso.concurso.request.ConcursoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConcursoConverterTest {

    @Mock
    private DataConverter dataConverter;

    @InjectMocks
    private ConcursoConverter concursoConverter;

    private ConcursoRequest concursoRequest;
    
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        concursoRequest = new ConcursoRequest();
        concursoRequest.setId(1L);
        concursoRequest.setNome("Concurso ABC");
        concursoRequest.setDataProvaDate("2024-12-10");
        concursoRequest.setPercentualEstudadoFloat(75.0f);
        concursoRequest.setUserId(1L);
    }

    @Test
    public void testConcursoRequestToConcurso() {
    	
    	User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("Test User");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(dataConverter.converterStringParaDate(concursoRequest.getDataProvaDate())).thenReturn(new Date());
        

        Concurso concurso = concursoConverter.concursoRequestToConcurso(concursoRequest);

        verify(dataConverter).converterStringParaDate(concursoRequest.getDataProvaDate());

    
        assertNotNull(concurso);
        assertEquals(1L, concurso.getId());
        assertEquals("Concurso ABC", concurso.getNome());
        assertNotNull(concurso.getDataProvaDate());
        assertEquals(75.0f, concurso.getPercentualEstudadoFloat());
        assertNotNull(concurso.getUser());
    }
}
