package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AbstractEntityAvaliacaoAntropometrica;
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
public class ProtocoloGuedes extends AbstractEntityAvaliacaoAntropometrica {

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

	
}
