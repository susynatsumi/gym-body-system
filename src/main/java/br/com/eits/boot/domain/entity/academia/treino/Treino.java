package br.com.eits.boot.domain.entity.academia.treino;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.boot.domain.entity.academia.AbstractEntityAcademia;
import br.com.eits.boot.domain.entity.academia.Academia;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class Treino extends AbstractEntityAcademia {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -152454134334981900L;

	// -----------------------------------
	// -------------- ATRIBUTOS ----------
	// -----------------------------------
	
	// nome do treino
	@NotEmpty
	@Column( nullable = false, length = 50 )
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
	@NotNull
	@Column(nullable = false)
	private LocalTime horaPrevistaInicio;
	
	// hora prevista de termino dos treinos
	@NotNull
	@Column( nullable = false)
	private LocalTime horaPrevistaTermino;
	
	
	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "treino",
		orphanRemoval = false,
		targetEntity = TreinoExercicio.class
	)
	private List<TreinoExercicio> treinoExercicios;

	@OneToMany( 
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "treino",
		orphanRemoval = false,
		targetEntity = TreinoData.class
	)
	private List<TreinoData> treinoDatas;
	
	/**
	 * Dias selecionados na tela
	 */
	@Transient
	private List<DiaSemana> diasSemanaSelecionados;
	
	// ---------------------------------------------
	// -------------- CONSTRUTORES -----------------
	// ---------------------------------------------
	
	/**
	 * Constructor Default
	 */
	public Treino(){
		super();
	}
	
	/**
	 * 
	 * Constructor com Id 
	 * 
	 * @param id
	 */
	public Treino( Long id ){
		super(id);
	}

	/**
	 * 
	 * Constructor com todos os campos
	 * 
	 * @param nome
	 * @param dataInicio
	 * @param dataFim
	 * @param horaPrevistaInicio
	 * @param horaPrevistaTermino
	 * @param treinoExercicios
	 * @param treinoDatas
	 * @param diasSemanaSelecionados
	 */
	public Treino(
		String nome, 
		LocalDate dataInicio,
		LocalDate dataFim,
		LocalTime horaPrevistaInicio, 
		LocalTime horaPrevistaTermino,
		List<TreinoExercicio> treinoExercicios, 
		List<DiaSemana> diasSemanaSelecionados,
		Academia academia
	) {
		super(academia);
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.horaPrevistaInicio = horaPrevistaInicio;
		this.horaPrevistaTermino = horaPrevistaTermino;
		this.treinoExercicios = treinoExercicios;
		this.diasSemanaSelecionados = diasSemanaSelecionados;
	}}
