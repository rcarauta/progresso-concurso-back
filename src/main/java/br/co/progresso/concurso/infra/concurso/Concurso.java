package br.co.progresso.concurso.infra.concurso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.co.progresso.concurso.infra.user.User;
import br.co.progresso.concurso.infra.concurso_disciplina_materia.ConcursoDisciplinaMateria;
import br.co.progresso.concurso.infra.disciplina.Disciplina;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contest")
public class Concurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome não pode ser vazio.")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "A data da prova não pode ser nula.")
    @FutureOrPresent(message = "A data da prova não pode ser anterior à data de hoje.")
    private Date dataProvaDate;

    @Column(nullable = false)
    @NotNull(message = "O percentual estudado não pode ser nulo.")
    @DecimalMin(value = "0.1", message = "O percentual estudado deve ser maior que 0.")
    private Float percentualEstudadoFloat;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "O usuário não pode ser nulo.")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contest_disciplina",
            joinColumns = @JoinColumn(name = "contest_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    	)
    private List<Disciplina> disciplinas = new ArrayList<>();
    
    @OneToMany(mappedBy = "concurso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ConcursoDisciplinaMateria> contestDisciplinaMaterias;

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

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }


	public Set<ConcursoDisciplinaMateria> getContestDisciplinaMaterias() {
		return contestDisciplinaMaterias;
	}


	public void setContestDisciplinaMaterias(Set<ConcursoDisciplinaMateria> contestDisciplinaMaterias) {
		this.contestDisciplinaMaterias = contestDisciplinaMaterias;
	}

}
