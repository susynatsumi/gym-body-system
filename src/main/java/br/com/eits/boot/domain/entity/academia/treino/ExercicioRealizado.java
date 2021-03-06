package br.com.eits.boot.domain.entity.academia.treino;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.common.domain.entity.AbstractEntity;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=ExercicioRealizado.class)
public class ExercicioRealizado extends AbstractEntity {

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
		fetch = FetchType.EAGER,
		optional = false, 
		targetEntity = TreinoData.class
	)
	private TreinoData treinoData;
	
	// exercicio do treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.EAGER,
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
	public ExercicioRealizado(){
		super();
	}
	
	/**
	 * Constructor soente com Id 
	 * 
	 * @param id
	 */
	public ExercicioRealizado( Long id ){
		super(id);
	}

	/**
	 * Constructor com todos os campos
	 * @param completo
	 * @param treinoData
	 * @param treinoExercicio
	 */
	public ExercicioRealizado(
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
