package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@Table
@EqualsAndHashCode(callSuper=true)
@DataTransferObject
public class AvaliacaoAntropometrica extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1136316925680643949L;
	
	// medidas das dobras cutaneas
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = DobrasCutaneas.class,
//		mappedBy = "abstractEntityAvaliacaoAntropometrica",
		orphanRemoval = true
	)
	private DobrasCutaneas dobrasCutaneas;
//	
//	// dados de indice de massa corporal
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = true,
		targetEntity = IndiceMassaCorporal.class,
//		mappedBy = "abstractEntityAvaliacaoAntropometrica",
		orphanRemoval = true
	)
	private IndiceMassaCorporal indiceMassaCorporal;
//	
//	// gordura siri
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = true,
		targetEntity = PredicaoGorduraSiri.class,
//		mappedBy = "abstractEntityAvaliacaoAntropometrica",
		orphanRemoval = true
	)
	private PredicaoGorduraSiri predicaoGorduraSiri;
//	
//	// referencia da avaliacao fisica
//	@NotNull	
	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn
	private AvaliacaoFisica avaliacaoFisica;

	/**
	 * Resultado do cálculo de gordura
	 */
	@Column
	private Double densidadeCorporal;
	
	// ------------------------------------------------
	// ----------------- CONSTRUTORS ------------------
	// ------------------------------------------------
	

	/**
	 * @param id
	 * @param dobrasCutaneas
	 * @param indiceMassaCorporal
	 * @param predicaoGorduraSiri
	 */
	public AvaliacaoAntropometrica(
		Long id, 
		DobrasCutaneas dobrasCutaneas,
		IndiceMassaCorporal indiceMassaCorporal, 
		PredicaoGorduraSiri predicaoGorduraSiri
	) {
		super(id);
		this.dobrasCutaneas = dobrasCutaneas;
		this.indiceMassaCorporal = indiceMassaCorporal;
		this.predicaoGorduraSiri = predicaoGorduraSiri;
	}
	
	/**
	 * 
	 * @param id
	 */
	public AvaliacaoAntropometrica( Long id ) {
		super(id);
	}

	/**
	 * Constructor default
	 */
	public AvaliacaoAntropometrica() {
	}
	
}
