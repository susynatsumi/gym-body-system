package br.com.eits.boot.domain.entity.academia.treino;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.AbstractEntityAcademia;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class TreinoData extends AbstractEntityAcademia {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3131215350571042271L;

	
	// ------------------------------------
	// ------ ATRIBUTOS -------------------
	// ------------------------------------
	
	// data para realização de um treino
	@NotNull
	@Column( nullable = false )
	public LocalDate data;
	
	// hora de inicio da realizacao do treino
	@NotNull
	@Column( nullable = false )
	public LocalTime horaInicio;
	
	// hora de termino da realização do treino
	@NotNull
	@Column( nullable = false)
	public LocalTime horaTermino;
	
	// indica se o treino foi completo ou não
	@NotNull
	@Column( nullable = false )
	public Boolean completo;
	
	// referencia do treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.MERGE,
		targetEntity = Treino.class,
		optional = false
	)
	private Treino treino;
	
	// Dia da semana 
	@Enumerated(EnumType.ORDINAL)
	@NotNull 
	@Column(nullable = false)
	private DiaSemana diaSemana;
	
	
	// --------------------------------
	// ------------ CONSTRUCTORS ------
	// --------------------------------
	
	/**
	 * Constructor default
	 */
	public TreinoData(){
		super();
	}
	
	public TreinoData( Long id ){
		super(id);
	}

	
	/**
	 * 
	 * Constructor com todos os fields
	 * 
	 * @param data
	 * @param horaInicio
	 * @param horaTermino
	 * @param completo
	 * @param treino
	 * @param diaSemana
	 */
	public TreinoData(
		LocalDate data,
		LocalTime horaInicio, 
		LocalTime horaTermino,
		Boolean completo, 
		Treino treino, 
		DiaSemana diaSemana
	) {
		super();
		this.data = data;
		this.horaInicio = horaInicio;
		this.horaTermino = horaTermino;
		this.completo = completo;
		this.treino = treino;
		this.diaSemana = diaSemana;
	}
	
	
}
