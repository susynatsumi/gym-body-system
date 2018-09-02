package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@DataTransferObject
@Table
@Audited
@EqualsAndHashCode(callSuper = true)
public class Perimetria extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*-------------------------------------------------------------------
	 *				 	    ATRIBUTOS PERIMETRIA 
	 *-------------------------------------------------------------------*/

	// PERIMETRIA, realizar calculos
	// ---------------------------------------------
	// --------------------- medidas ---------------
	// ---------------------------------------------
	@NotNull
	@Column(nullable = false)
	private Double pescoco;
	
	@NotNull
	@Column(nullable = false)
	private Double torax;
	
	@NotNull
	@Column(nullable = false)
	private Double bracoDireitoRelaxado;
	
	@NotNull
	@Column(nullable = false)
	private Double bracoEsquerdoRelaxado;
	
	@NotNull
	@Column(nullable = false)
	private Double bracoDireitoContraido;
	
	@NotNull
	@Column(nullable = false)
	private Double bracoEsquerdoContraido;
	
	@NotNull
	@Column(nullable = false)
	private Double antebracoDireito;
	
	@NotNull
	@Column(nullable = false)
	private Double antebracoEsquerdo;
	
	@NotNull
	@Column(nullable = false)
	private Double cintura;
	
	@NotNull
	@Column(nullable = false)
	private Double abdomen;
	
	@NotNull
	@Column(nullable = false)
	private Double quadril;
	
	@NotNull
	@Column(nullable = false)
	private Double coxaProximalDireita;
	
	@NotNull
	@Column(nullable = false)
	private Double coxaProximalEsquerda;
	
	@NotNull
	@Column(nullable = false)
	private Double coxaMediaDireita;
	
	@NotNull
	@Column(nullable = false)
	private Double coxaMediaEsquerda;
	
	@NotNull
	@Column(nullable = false)
	private Double coxaDistalDireita;
	
	@NotNull
	@Column(nullable = false)
	private Double coxaDistalEsquerda;
	
	@NotNull
	@Column(nullable = false)
	private Double panturrilhaDireita;
	
	@NotNull
	@Column(nullable = false)
	private Double panturrilhaEsquerda;
	
	@NotNull
	@OneToOne(
//		cascade = CascadeType.ALL,
		fetch = FetchType.EAGER,
		mappedBy = "perimetria",
		optional = false,
		orphanRemoval = false,
		targetEntity = AvaliacaoFisica.class
	) 
	@PrimaryKeyJoinColumn
	private AvaliacaoFisica avaliacaoFisica;

	
	// ---------------------------------------------------------
	// -------------------- CONSTRUCTORS -----------------------
	// ---------------------------------------------------------
	
	/**
	 * @param id
	 * @param pescoco
	 * @param torax
	 * @param bracoDireitoRelaxado
	 * @param bracoEsquerdoRelaxado
	 * @param bracoDireitoContraido
	 * @param bracoEsquerdoContraido
	 * @param antebracoDireito
	 * @param antebracoEsquerdo
	 * @param cintura
	 * @param abdomen
	 * @param quadril
	 * @param coxaProximalDireita
	 * @param coxaProximalEsquerda
	 * @param coxaMediaDireita
	 * @param coxaMediaEsquerda
	 * @param coxaDistalDireita
	 * @param coxaDistalEsquerda
	 * @param panturrilhaDireita
	 * @param panturrilhaEsquerda
	 * @param avaliacaoFisica
	 */
	public Perimetria(
			Long id,
			Double pescoco, 
			Double torax,
			Double bracoDireitoRelaxado, 
			Double bracoEsquerdoRelaxado,
			Double bracoDireitoContraido, 
			Double bracoEsquerdoContraido,
			Double antebracoDireito, 
			Double antebracoEsquerdo, 
			Double cintura,
			Double abdomen, 
			Double quadril, 
			Double coxaProximalDireita,
			Double coxaProximalEsquerda, 
			Double coxaMediaDireita,
			Double coxaMediaEsquerda, 
			Double coxaDistalDireita,
			Double coxaDistalEsquerda, 
			Double panturrilhaDireita,
			Double panturrilhaEsquerda, 
			AvaliacaoFisica avaliacaoFisica
	) {
		super( id );
		this.pescoco = pescoco;
		this.torax = torax;
		this.bracoDireitoRelaxado = bracoDireitoRelaxado;
		this.bracoEsquerdoRelaxado = bracoEsquerdoRelaxado;
		this.bracoDireitoContraido = bracoDireitoContraido;
		this.bracoEsquerdoContraido = bracoEsquerdoContraido;
		this.antebracoDireito = antebracoDireito;
		this.antebracoEsquerdo = antebracoEsquerdo;
		this.cintura = cintura;
		this.abdomen = abdomen;
		this.quadril = quadril;
		this.coxaProximalDireita = coxaProximalDireita;
		this.coxaProximalEsquerda = coxaProximalEsquerda;
		this.coxaMediaDireita = coxaMediaDireita;
		this.coxaMediaEsquerda = coxaMediaEsquerda;
		this.coxaDistalDireita = coxaDistalDireita;
		this.coxaDistalEsquerda = coxaDistalEsquerda;
		this.panturrilhaDireita = panturrilhaDireita;
		this.panturrilhaEsquerda = panturrilhaEsquerda;
		this.avaliacaoFisica = avaliacaoFisica;
	}

	/**
	 * 
	 * @param id
	 */
	public Perimetria( Long id ) {
		super( id );
	}

	/**
	 * Constructor default
	 */
	public Perimetria() {
	}
}
