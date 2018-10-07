package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.calculos;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;

public class CalculoProtocoloGuedes extends AbstractCalculoProtocoloAvaliacaoFisica{

	/**
	 * Realiza o cálculo da densidade corporal 
	 * 	com protocolo de guedes para pessoas do sexo masculino
	 */
	@Override
	Double equacaoHomem(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		
		DobrasCutaneas dobrasCutaneas = avaliacaoAntropometrica.getDobrasCutaneas();
		
		Double soma = (
			dobrasCutaneas.getTricipal() 
			+ dobrasCutaneas.getSupraIliaca() 
			+ dobrasCutaneas.getAbdominal()
		);
		
		Double log = Math.log10(soma);
		
		return ( 1.1714d - ( 0.0671d * log ) ); 
		
	}

	/**
	 * Realiza o cálculo da densidade corporal com 
	 * 	protocolo de guedes para pessoas do sexo feminino
	 */
	@Override
	Double equacaoMulher(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		
		DobrasCutaneas dobrasCutaneas = avaliacaoAntropometrica.getDobrasCutaneas();
		
		Double soma = (
			dobrasCutaneas.getCoxa()
			+ dobrasCutaneas.getSupraIliaca()
			+ dobrasCutaneas.getSubescapular()
		);
			
		Double log = Math.log10(soma);
		
		return ( 1.1665d - ( 0.0706d * log ) );
	}

}
