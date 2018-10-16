package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.TipoProtocolo;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
@Table
@EqualsAndHashCode(callSuper=true, exclude = "avaliacaoFisica")
@DataTransferObject
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=AvaliacaoAntropometrica.class)
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
//	@LazyToOne foi necessario para não carregar a avaliacao fisica e fazer um loop infinito
//	@NotNull	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY, required = false)
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private AvaliacaoFisica avaliacaoFisica;

	@NotNull
	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoProtocolo tipoProtocolo;
	
	/**
	 * Resultado do cálculo de gordura
	 */
	@NotNull
	@Column
	private Double densidadeCorporal;
	
	// -----------------------------------------------
	// -------------------- COMPORTAMENTOS------------
	// -----------------------------------------------
	/**
	 * Cancula a densidade corporal, de acordo com o protocolo selecionado
	 */
	public void calculaDensidadeCorporal(){
		this.densidadeCorporal = this.tipoProtocolo.realizaCalculo(this);
		if(this.densidadeCorporal != null){
			this.densidadeCorporal = Math.scalb(this.densidadeCorporal, 2);
		}
	}
	
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
		PredicaoGorduraSiri predicaoGorduraSiri,
		Double densidadeCorporal,
		TipoProtocolo tipoProtocolo
	) {
		super(id);
		this.dobrasCutaneas = dobrasCutaneas;
		this.indiceMassaCorporal = indiceMassaCorporal;
		this.predicaoGorduraSiri = predicaoGorduraSiri;
		this.densidadeCorporal = densidadeCorporal;
		this.tipoProtocolo = tipoProtocolo;
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
