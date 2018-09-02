package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Audited
@DataTransferObject
@Table
@EqualsAndHashCode(callSuper = true)
public class PredicaoGorduraSiri extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4652700273870748551L;
	
	/*-------------------------------------------------------------------
	 *				    ATRIBUTOS CALCULO GORDURA
	 *-------------------------------------------------------------------*/

	//%Gordura = [(4,95/Densidade Corporal) - 4,50] x 100
//	@NotNull
//	@Column(nullable = false)
//	private Double siri;
	
	@NotNull
	@Column(nullable = false)
	private Double densidadeCorporal; //é a medida feita pelo resultado dos protocolos aplicados
	
	@NotNull
	@Column(nullable = false)
	private Double gordura; //medida final da gordura pelo resultado da divisão e porcentagem
	
	// referencia ao protocolo
	@NotNull
	@OneToOne(
		cascade = CascadeType.MERGE,
		fetch = FetchType.EAGER,
		mappedBy = "predicaoGorduraSiri",
		optional = false,
		orphanRemoval = false,
		targetEntity = AbstractEntityAvaliacaoAntropometrica.class
	) 
	private AbstractEntityAvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica;

	/*-------------------------------------------------------------------
	 *			MÉTODOS DE CÁLCULOS E DENSIDADE CORPORAL
	 *-------------------------------------------------------------------*/

	// ------------------------------------------------------------------
	// ------------------ CONSTRUCTORS ----------------------------------
	// ------------------------------------------------------------------
	
	/**
	 * @param id
	 * @param densidadeCorporal
	 * @param gordura
	 * @param abstractEntityAvaliacaoAntropometrica
	 */
	public PredicaoGorduraSiri(
		Long id, 
		Double densidadeCorporal, 
		Double gordura,
		AbstractEntityAvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica
	) {
		super(id);
		this.densidadeCorporal = densidadeCorporal;
		this.gordura = gordura;
		this.abstractEntityAvaliacaoAntropometrica = abstractEntityAvaliacaoAntropometrica;
	}
	
	/**
	 * 
	 * @param id
	 */
	public PredicaoGorduraSiri( Long id ) {
		super( id );
	}
	
	/**
	 * Constructor default
	 */
	public PredicaoGorduraSiri() {
	}
	
	
}
