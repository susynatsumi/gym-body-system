package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import org.directwebremoting.annotations.DataTransferObject;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.calculos.AbstractCalculoProtocoloAvaliacaoFisica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.calculos.CalculoProtocoloGuedes;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.calculos.CalculoProtocoloPollock;

@DataTransferObject(type = "enum")
public enum TipoProtocolo {
	
	// tipos do enum
	GUEDES(new CalculoProtocoloGuedes()),
	POLLOCK(new CalculoProtocoloPollock());
	
	// ------------------------------------------
	// -------------- constructor ---------------
	// ------------------------------------------
	
	/**
	 * 
	 * @param calculoProtocoloSelecionado
	 */
	private TipoProtocolo(AbstractCalculoProtocoloAvaliacaoFisica calculoProtocoloSelecionado) {
		this.calculoProtocoloSelecionado = calculoProtocoloSelecionado;
	}
	
	// calculo de cada protocolo
	private final AbstractCalculoProtocoloAvaliacaoFisica calculoProtocoloSelecionado;
	
//	public AbstractCalculoProtocoloAvaliacaoFisica getCalculoProtocoloSelecionado() {
//		return calculoProtocoloSelecionado;
//	}
	
	/**
	 * 
	 * Realiza o calculo da densidade corporal de acordo como tipo de protocolo selecionado
	 * 
	 * @param avaliacaoAntropometrica
	 * @return
	 */
	public Double realizaCalculo(AvaliacaoAntropometrica avaliacaoAntropometrica){
		return this.calculoProtocoloSelecionado
				.realizaCaluloPercentualGordura(
					avaliacaoAntropometrica
				);
	}
	
}
