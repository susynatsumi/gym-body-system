package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

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

	// DOBRAS fazer com que adicione valores correspondentes as suas dobras 
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double tricipal; //atras do braco medida do tríceps
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double bicital; //frente do braco medida do bíceps

	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double subescapular; //a face costal da escápula

	@Min(0)
	@Column( nullable = false )
	private Double peitoral; // medida do peitoral
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double toracica; //à porção da coluna vertebral que fica entre a região cervical e a região lombar
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double axilarMedia; //fica abaixo da axila, localizada no ponto de intersecção entre a linha axilar média e uma linha imaginária transversal na altura do apêndice xifóide do esterno
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double supraIliaca; //obtida obliquamente em relação ao eixo longitudinal, na metade da distância entre o último arco costal e a crista ilíaca, sobre a linha axilar medial
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double abdominal; //media aproximadamente a dois centímetros à direita da cicatriz umbilical, paralelamente ao eixo longitudinal.
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double coxa; //membro inferior direito à frente, com uma semi-flexão do joelho, e manter o peso do corpo no membro inferior esquerdo
	
	@Min(0)
	@NotNull
	@Column( nullable = false )
	private Double panturrilha; //o avaliado deve estar sentado, com a articulação do joelho em flexão de 90 graus, o tornozelo em posição anatômica e o pé sem apoio. A dobra é pinçada no ponto de maior perímetro da perna, com o polegar da mão esquerda apoiado na borda medial da tíbia.

//	@NotNull
//	@OneToOne(
//		cascade = CascadeType.MERGE,
//		fetch = FetchType.LAZY,
//		optional = false,
//		targetEntity = AvaliacaoAntropometrica.class,
//		mappedBy = "dobrasCutaneas",
//		orphanRemoval = false
//	)
//	private AvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica;
	
	/*-------------------------------------------------------------------
	 *				     DOBRAS CÁLCULOS
	 *-------------------------------------------------------------------*/

	// ---------------------------------------------------
	// -------------------- CONSTRUCTOR ------------------
	// ---------------------------------------------------
	
	/**
	 * @param id
	 * @param tricipal
	 * @param bicital
	 * @param subescapular
	 * @param peitoral
	 * @param toracica
	 * @param axilarMedia
	 * @param supraIliaca
	 * @param abdominal
	 * @param coxa
	 * @param panturrilha
	 */
	public DobrasCutaneas(
		Long id, 
		Double tricipal, 
		Double bicital, 
		Double subescapular,
		Double peitoral, 
		Double toracica, 
		Double axilarMedia, 
		Double supraIliaca,
		Double abdominal, 
		Double coxa, 
		Double panturrilha
	) {
		super(id);
		this.tricipal = tricipal;
		this.bicital = bicital;
		this.subescapular = subescapular;
		this.peitoral = peitoral;
		this.toracica = toracica;
		this.axilarMedia = axilarMedia;
		this.supraIliaca = supraIliaca;
		this.abdominal = abdominal;
		this.coxa = coxa;
		this.panturrilha = panturrilha;
	}
	
	/**
	 * 
	 * @param id
	 */
	public DobrasCutaneas( Long id ) {
		super(id);
	}
	
	/**
	 * Constructor default
	 */
	public DobrasCutaneas() {
	}
	
}
