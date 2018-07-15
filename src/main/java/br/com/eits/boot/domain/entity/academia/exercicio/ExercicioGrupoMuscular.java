package br.com.eits.boot.domain.entity.academia.exercicio;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(uniqueConstraints = {
	@UniqueConstraint( columnNames = {"exercicio_id", "grupo_muscular_id" } )
})
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class ExercicioGrupoMuscular extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -641496184043741631L;

	// --------------------------
	// ------- ATRIBUTOS --------
	// --------------------------
	
	@ManyToOne( 
		fetch = FetchType.EAGER,
		optional = false,
		targetEntity = Exercicio.class,
		cascade = CascadeType.MERGE
	)
	private Exercicio exercicio;
	
	@ManyToOne(
	    fetch = FetchType.EAGER,
	    optional = false,
	    targetEntity = GrupoMuscular.class,
	    cascade = CascadeType.MERGE
	)
	private GrupoMuscular grupoMuscular;
	
	// -------------------------------------
	// ------- CONSTRUTORES ----------------
	// -------------------------------------
	
	/**
	 * Constructor Default
	 */
	public ExercicioGrupoMuscular(){
		super();
	}
	
	/**
	 * Constructor somente com id 
	 * 
	 * @param id
	 */
	public ExercicioGrupoMuscular( Long id ){
		super(id);
	}
	
	/**
	 * Constructor com todos os atributos
	 * 
	 * @param exercicio
	 * @param grupoMuscular
	 * 
	 */
	public ExercicioGrupoMuscular(Exercicio exercicio, GrupoMuscular grupoMuscular) {
		super();
		this.exercicio = exercicio;
		this.grupoMuscular = grupoMuscular;
	}
	
	
}
