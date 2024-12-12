package br.co.progresso.concurso.application;

public class DisciplinaDuplicadaException extends RuntimeException {
    public DisciplinaDuplicadaException(String nome) {
        super("Disciplina duplicada: "+ nome);
    }
}