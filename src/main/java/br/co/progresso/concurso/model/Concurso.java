package br.co.progresso.concurso.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contest")
public class Concurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String nome;

	@Column
	private Date dataProvaDate;

	@Column
	private Float percentualEstudadoFloat;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataProvaDate() {
		return dataProvaDate;
	}

	public void setDataProvaDate(Date dataProvaDate) {
		this.dataProvaDate = dataProvaDate;
	}

	public Float getPercentualEstudadoFloat() {
		return percentualEstudadoFloat;
	}

	public void setPercentualEstudadoFloat(Float percentualEstudadoFloat) {
		this.percentualEstudadoFloat = percentualEstudadoFloat;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
