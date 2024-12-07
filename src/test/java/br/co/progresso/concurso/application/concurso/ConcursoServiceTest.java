package br.co.progresso.concurso.application.concurso;

import br.co.progresso.concurso.infra.concurso.Concurso;

import br.co.progresso.concurso.infra.concurso.ConcursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // Anotação para permitir o uso do Mockito no JUnit 5
public class ConcursoServiceTest {

    @Mock
    private ConcursoRepository concursoRepostiory;

    @Mock
    private ConcursoConverter concursoConverter;

    @InjectMocks
    private ConcursoService concursoService;

    private List<Concurso> listaConcursoMock;
    private List<ConcursoRequest> listaConcursoRequestMock;


    @BeforeEach
    public void setUp() {
        Concurso concurso = new Concurso();
        concurso.setId(1L);
        concurso.setNome("Concurso ABC");

        listaConcursoMock = Arrays.asList(concurso);

        ConcursoRequest concursoRequest = new ConcursoRequest();
        concursoRequest.setId(1L);
        concursoRequest.setNome("Concurso ABC");

        listaConcursoRequestMock = Arrays.asList(concursoRequest);

    }

    @Test
    public void testRecuperarTodosConcursos() {
        when(concursoRepostiory.findAll()).thenReturn(listaConcursoMock);
        when(concursoConverter.listConcursoToListConcursoRequest(listaConcursoMock)).thenReturn(listaConcursoRequestMock);
        List<ConcursoRequest> result = concursoService.recuperarTodosConcursos();

        verify(concursoRepostiory).findAll();
        verify(concursoConverter).listConcursoToListConcursoRequest(listaConcursoMock);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Concurso ABC", result.get(0).getNome());
    }

    @Test
    public void testRecuperarTodosConcursosDoUsuario() {
        Long userId = 1L;

        when(concursoRepostiory.findByUserId(userId)).thenReturn(listaConcursoMock);
        when(concursoConverter.listConcursoToListConcursoRequest(listaConcursoMock)).thenReturn(listaConcursoRequestMock);

        List<ConcursoRequest> result = concursoService.recuperarTodosConcursosDoUsuario(userId);

        verify(concursoRepostiory).findByUserId(userId);
        verify(concursoConverter).listConcursoToListConcursoRequest(listaConcursoMock);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Concurso ABC", result.get(0).getNome());
        assertEquals(1L, result.get(0).getId());
    }



}

