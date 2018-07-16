package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.AvaliacaoAntropometrica;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table
@Audited
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class DobrasCutaneas extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*-------------------------------------------------------------------
	 *				    	ATRIBUTOS DOBRAS CUTANEAS
	 *-------------------------------------------------------------------*/

	//TODO DOBRAS fazer com que adicione valores correspondentes as suas dobras 
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal tricipal; //atras do braco medida do tríceps
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal bicital; //frente do braco medida do bíceps

	@NotNull
	@Column(nullable = false)
	private BigDecimal subescapular; //a face costal da escápula

	@NotNull
	@Column(nullable = false)
	private BigDecimal toracica; //à porção da coluna vertebral que fica entre a região cervical e a região lombar
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal axilarMedia; //fica abaixo da axila, localizada no ponto de intersecção entre a linha axilar média e uma linha imaginária transversal na altura do apêndice xifóide do esterno
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal supraIliaca; //obtida obliquamente em relação ao eixo longitudinal, na metade da distância entre o último arco costal e a crista ilíaca, sobre a linha axilar medial
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal abdominal; //media aproximadamente a dois centímetros à direita da cicatriz umbilical, paralelamente ao eixo longitudinal.
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxa; //membro inferior direito à frente, com uma semi-flexão do joelho, e manter o peso do corpo no membro inferior esquerdo
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal panturrilhaMedial; //o avaliado deve estar sentado, com a articulação do joelho em flexão de 90 graus, o tornozelo em posição anatômica e o pé sem apoio. A dobra é pinçada no ponto de maior perímetro da perna, com o polegar da mão esquerda apoiado na borda medial da tíbia.
	
	@ManyToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = AvaliacaoAntropometrica.class
	)
	private AvaliacaoAntropometrica avaliacaoAntropometrica;
	
	/*-------------------------------------------------------------------
	 *				    	TODO DOBRAS CÁLCULOS
	 *-------------------------------------------------------------------*/
}
