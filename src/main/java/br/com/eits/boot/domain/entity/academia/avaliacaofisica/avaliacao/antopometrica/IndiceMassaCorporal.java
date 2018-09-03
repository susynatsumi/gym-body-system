package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import br.com.eits.common.application.i18n.MessageSourceHolder;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class IndiceMassaCorporal extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2658777917083420385L;

	// altura em metros da pessoa para calculo imc
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private BigDecimal altura;
	
	// peso em kg pessoa para calculo imc
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private BigDecimal peso;
	
//	@NotNull
//	@OneToOne(
//		cascade = CascadeType.MERGE,
//		fetch = FetchType.LAZY,
//		mappedBy = "indiceMassaCorporal",
//		optional = false,
//		orphanRemoval = false,
//		targetEntity = AvaliacaoAntropometrica.class
//	)
//	private AvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica;
	
	
	// ------------------------------------------------
	// ------------------------ METODOS ---------------
	// ------------------------------------------------
	
	/**
	 * 
	 * Realiza o calculo do imc
	 * 
	 * @return
	 */
	@Transient
	public BigDecimal calculaImc(){
		
		Assert.isTrue(
			altura != null && altura.longValue() > 0, 
			MessageSourceHolder.translate("indice.massa.corporal.altura.not.null")
		);
		
		Assert.notNull(
			peso != null && peso.longValue() > 0,
			MessageSourceHolder.translate("indice.massa.corporal.peso.not.null") 
		);
		
		BigDecimal alturaAoQuadrado = altura.multiply(altura);
		
		return peso.divide(alturaAoQuadrado, BigDecimal.ROUND_HALF_EVEN);
		
	}

	// -------------------------------------------------------
	// ------------------- CONSTRUCTORS ----------------------
	// -------------------------------------------------------
	
	/**
	 * @param id
	 * @param altura
	 * @param peso
	 */
	public IndiceMassaCorporal(
		Long id, 
		BigDecimal altura, 
		BigDecimal peso
	) {
		super(id);
		this.altura = altura;
		this.peso = peso;
	}
	
	/**
	 * 
	 * @param id
	 */
	public IndiceMassaCorporal( Long id ) {
		super(id);
	}

	/**
	 * Constructor default
	 */
	public IndiceMassaCorporal() {
	}
	
}
