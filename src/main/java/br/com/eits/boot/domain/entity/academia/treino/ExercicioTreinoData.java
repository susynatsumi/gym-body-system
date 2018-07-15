package br.com.eits.boot.domain.entity.academia.treino;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.AbstractEntityAcademia;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(uniqueConstraints = {
	@UniqueConstraint(
		columnNames = {"treino_data_id", "treino_exercicio_id"}
	)
})
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class ExercicioTreinoData extends AbstractEntityAcademia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6871636689400326371L;

	// -------------------------------------------
	// ------- ATRIBUTOS -------------------------
	// -------------------------------------------
	
	// se o aluno completou o exercicio do treino
	@NotNull
	@Column( nullable = false )
	private Boolean completo;
	
	// data de realizacao do treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.REFRESH,
		optional = false, 
		targetEntity = TreinoData.class
	)
	private TreinoData treinoData;
	
	// exercicio do treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.REFRESH,
		optional = false,
		targetEntity = TreinoExercicio.class
	)
	private TreinoExercicio treinoExercicio;
	
	// ----------------------------------------
	// ----------- CONSTRUCTORS ---------------
	// ----------------------------------------
	
	/**
	 * Constructor Default
	 */
	public ExercicioTreinoData(){
		super();
	}
	
	/**
	 * Constructor soente com Id 
	 * 
	 * @param id
	 */
	public ExercicioTreinoData( Long id ){
		super(id);
	}

	/**
	 * Constructor com todos os campos
	 * @param completo
	 * @param treinoData
	 * @param treinoExercicio
	 */
	public ExercicioTreinoData(
		Boolean completo, 
		TreinoData treinoData,
		TreinoExercicio treinoExercicio
	) {
		super();
		this.completo = completo;
		this.treinoData = treinoData;
		this.treinoExercicio = treinoExercicio;
	}
	
}
