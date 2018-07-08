package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	//TODO PERIMETRIA, realizar calculos
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal pescoco;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal torax;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal bracoDireitoRelaxado;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal bracoEsquerdoRelaxado;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal bracoDireitoContraido;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal bracoEsquerdoContraido;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal antebracoDireito;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal antebracoEsquerdo;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal cintura;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal abdomen;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal quadril;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxaProximalDireita;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxaProximalEsquerda;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxaMediaDireita;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxaMediaEsquerda;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxaDistalDireita;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal coxaDistalEsquerda;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal panturrilhaDireita;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal panturrilhaEsquerda;
}
