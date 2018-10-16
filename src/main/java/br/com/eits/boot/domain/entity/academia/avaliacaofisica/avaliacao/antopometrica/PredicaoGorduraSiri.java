package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Audited
@DataTransferObject
@Table
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=PredicaoGorduraSiri.class)
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
//	@NotNull
//	@OneToOne(
//		cascade = CascadeType.MERGE,
//		fetch = FetchType.LAZY,
//		mappedBy = "predicaoGorduraSiri",
//		optional = false,
//		orphanRemoval = false,
//		targetEntity = AvaliacaoAntropometrica.class
//	) 
//	private AvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica;

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
		Double gordura
//		AvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica
	) {
		super(id);
		this.densidadeCorporal = densidadeCorporal;
		this.gordura = gordura;
//		this.abstractEntityAvaliacaoAntropometrica = abstractEntityAvaliacaoAntropometrica;
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
