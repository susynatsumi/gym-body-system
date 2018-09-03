package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
//import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn
public class ProtocoloGuedes extends AvaliacaoAntropometrica implements IProtocoloAvaliacaoAntropometrica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 902375995301369890L;

	@Transient
	@Override
	public void equacaoHomem() {
		// TODO verificar isso aqui como fazer 
	}

	@Transient
	@Override
	public void equacaoMulher() {
		// TODO verficar isso aqui como fazer
		
	}

	
	
	// -----------------------------------------------
	// ------------------- CONSTRUCTORS --------------
	// -----------------------------------------------
	
	/**
	 * @param id
	 * @param dobrasCutaneas
	 * @param indiceMassaCorporal
	 * @param predicaoGorduraSiri
	 */
	public ProtocoloGuedes(
		Long id, 
		DobrasCutaneas dobrasCutaneas, 
		IndiceMassaCorporal indiceMassaCorporal,
		PredicaoGorduraSiri predicaoGorduraSiri
	) {
		super(id, dobrasCutaneas, indiceMassaCorporal, predicaoGorduraSiri);
	}

	/**
	 * @param id
	 */
	public ProtocoloGuedes(Long id) {
		super(id);
	}

	/**
	 * Constructor default
	 */
	public ProtocoloGuedes() {
	}
	
	

	
}
