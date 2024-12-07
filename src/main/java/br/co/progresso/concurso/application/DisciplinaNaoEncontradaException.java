package br.co.progresso.concurso.application;

public class DisciplinaNaoEncontradaException extends RuntimeException {
    public DisciplinaNaoEncontradaException(Long id) {
        super("Disciplina não encontrada com o id: " + id);
    }
}