package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@EqualsAndHashCode(callSuper = true)
@DataTransferObject
public class ProtocoloAvaliacaoAntropometrica extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1830492172248973672L;
	
	/*-------------------------------------------------------------------
	 *				    ATRIBUTOS PROTOCOLO ANTROPOMETRICO
	 *-------------------------------------------------------------------*/

	private Double densidadeCorporal; //densidade corporal
	private Double dobrasCutaneas; //dobras cutaneas
	private Double gordura; //gordura
	private BigDecimal valor; //valor númerico

	/*-------------------------------------------------------------------
	 *				    TODO CALCULO DE DENSIDADE
	 *-------------------------------------------------------------------*/
	
	public void calculoDensidade(Double densidade, Double gordura) {
		
		
	}
}
