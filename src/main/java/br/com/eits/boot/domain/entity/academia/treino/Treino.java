package br.com.eits.boot.domain.entity.academia.treino;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( 
	callSuper = true,
	exclude = {"treinoExercicios"}
)
@DataTransferObject
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=Treino.class)
public class Treino extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -152454134334981900L;

	// -----------------------------------
	// -------------- ATRIBUTOS ----------
	// -----------------------------------
	
	// nome do treino
	@NotEmpty
	@Column( nullable = false, length = 200 )
	private String nome;
	
	// data de inicio do treino
	@NotNull
	@Column( nullable = false, updatable = false )
	private LocalDate dataInicio;
	
	// data de fim do treino
	@NotNull
	@Column( nullable = false, updatable = false )
	private LocalDate dataFim;
	
	// Hora prevista para inicio dos treinos
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "treino",
		orphanRemoval = false,
		targetEntity = TreinoExercicio.class
	)
	private List<TreinoExercicio> treinoExercicios;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	// aluno do treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = Pessoa.class
	) 
	private Pessoa aluno;
	
	// personal do treino
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	@NotNull
	@ManyToOne(
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = Pessoa.class
	) 
	private Pessoa personal;
	
	/**
	 * Dias selecionados na tela
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	@Transient
	private List<DiaSemana> diasSemanaSelecionados;
	
	
	@OneToMany(
		fetch = FetchType.LAZY,
		mappedBy = "treino",
		orphanRemoval = false,
		targetEntity = TreinoData.class
	)
	private List<TreinoData> treinoDatas;
	
	// ---------------------------------------------
	// -------------- CONSTRUTORES -----------------
	// ---------------------------------------------
	

	/**
	 * @param id
	 * @param nome
	 * @param dataInicio
	 * @param dataFim
	 * @param horaPrevistaInicio
	 * @param horaPrevistaTermino
	 * @param treinoExercicios
	 * @param aluno
	 * @param personal
	 * @param diasSemanaSelecionados
	 */
	public Treino(Long id, 
		String nome, 
		LocalDate dataInicio, 
		LocalDate dataFim,
		List<TreinoExercicio> treinoExercicios, 
		Pessoa aluno, 
		Pessoa personal,
		List<DiaSemana> diasSemanaSelecionados
	) {
		super(id);
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.treinoExercicios = treinoExercicios;
		this.aluno = aluno;
		this.personal = personal;
		this.diasSemanaSelecionados = diasSemanaSelecionados;
	}
	
	/**
	 * 
	 * @param id
	 */
	public Treino( Long id ) {
		super( id );
	}
	
	/**
	 * Constructor default
	 */
	public Treino() {
	}
}
