package br.co.progresso.concurso.infra.disciplina_materia;

import br.co.progresso.concurso.application.disciplina.CategoriaDisciplina;

public class DisciplinaMateriaDto {

	private String nome;
	private Long totalQuestoes;
	private Long questoesAcertadas;
	private CategoriaDisciplina categoria;

	public DisciplinaMateriaDto() {

	}

	public DisciplinaMateriaDto(String nome, Long totalQuestoes, Long questoesAcertadas,
			CategoriaDisciplina categoria) {
		this.nome = nome;
		this.totalQuestoes = totalQuestoes;
		this.questoesAcertadas = questoesAcertadas;
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTotalQuestoes() {
		return totalQuestoes;
	}

	public void setTotalQuestoes(Long totalQuestoes) {
		this.totalQuestoes = totalQuestoes;
	}

	public Long getQuestoesAcertadas() {
		return questoesAcertadas;
	}

	public void setQuestoesAcertadas(Long questoesAcertadas) {
		this.questoesAcertadas = questoesAcertadas;
	}

	public CategoriaDisciplina getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDisciplina categoria) {
		this.categoria = categoria;
	}

}
