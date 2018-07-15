package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.DobrasCutaneas;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.PredicaoGorduraSiri;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@EqualsAndHashCode(callSuper = true)
@DataTransferObject
public class AvaliacaoAntropometrica extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1830492172248973672L;
	
	/*-------------------------------------------------------------------
	 *				    ATRIBUTOS PROTOCOLO ANTROPOMETRICO
	 *-------------------------------------------------------------------*/

	/**
	 * @OneToMany(
			fetch = FetchType.LAZY,
			targetEntity = DensidadeCorporal.class,
			cascade = CascadeType.ALL, 
			mappedBy = "densidade", 
			orphanRemoval = true
	)	
	*/
	
	@OneToMany(
			fetch = FetchType.LAZY,
			targetEntity = PredicaoGorduraSiri.class,
			mappedBy = "avaliacaoAntropometrica",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private PredicaoGorduraSiri predicaoGorduraSiri; //densidade corporal
	
	@OneToMany(
			fetch = FetchType.LAZY,
			targetEntity = DobrasCutaneas.class,
			mappedBy = "avaliacaoAntrometrica",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private DobrasCutaneas dobrasCutaneas; //dobras cutaneas

}
