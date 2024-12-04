package br.co.progresso.concurso.exception;

public class ConcursoNaoEncontradoException extends RuntimeException {
    public ConcursoNaoEncontradoException(Long id) {
        super("Concurso não encontrado com o id: " + id);
    }
}