package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
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

	//TODO %Gordura = [(4,95/Densidade Corporal) - 4,50] x 100
	@NotNull
	@Column(nullable = false)
	private BigDecimal siri;
	
	@NotNull
	@Column(nullable = false)
	private Double densidadeCorporal; //é a medida feita pelo resultado dos protocolos aplicados
	
	@NotNull
	@Column(nullable = false)
	private Double gordura; //medida final da gordura pelo resultado da divisão e porcentagem
	
	/*-------------------------------------------------------------------
	 *			TODO MÉTODOS DE CÁLCULOS E DENSIDADE CORPORAL
	 *-------------------------------------------------------------------*/
	public void calculoSiri(BigDecimal pesoCorporal) {
		
		pesoCorporal = siri;

	}
}
