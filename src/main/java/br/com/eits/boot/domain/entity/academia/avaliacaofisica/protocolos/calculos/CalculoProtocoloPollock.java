package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.calculos;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;

public class CalculoProtocoloPollock extends AbstractCalculoProtocoloAvaliacaoFisica{

	/**
	 * De acordo com a fórumla 1,11200000 - [0,00043499 (X1) + 0,00000055 (X1)²] - [0,0002882 (X3)]
	 * onde x1 é a soma de algumas dobras e x3 é a idade em anos
	 * 
	 * retorna o percentual de gordura
	 */
	@Override
	Double equacaoHomem(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		
		final DobrasCutaneas dobrasCutaneas = avaliacaoAntropometrica.getDobrasCutaneas();
//		de acordo com a formula x1 é a soma das dobras...
		final Double x1 = (
			dobrasCutaneas.getToracica()
			+ dobrasCutaneas.getAxilarMedia()
			+ dobrasCutaneas.getTricipal()
			+ dobrasCutaneas.getSubescapular()
			+ dobrasCutaneas.getAbdominal()
			+ dobrasCutaneas.getCoxa()
		);
		
//		[0,00043499 (X1) + 0,00000055 (X1)²] 
		final Double parte1 = ( 0.00043499 * x1 ) + ( 0.00000055 * ( x1 * x1 ));
//		X3 idade em anos
		final int idadeAnos = avaliacaoAntropometrica
				.getAvaliacaoFisica()
				.getPessoa()
				.getIdadeAnos();
		
//		[0,0002882 (X3)]
		final Double parte2 = 0.0002882 *  idadeAnos;
		
//		DC = 1,11200000 - [0,00043499 (X1) + 0,00000055 (X1)²] - [0,0002882 (X3)]
		return 1.11200000 - parte1 - parte2;
		
	}

	/**
	 * De acordo com a fórumla DC = 1,0970 - [0,00046971 (X1) + 0,00000056 (X1)²] - [0,00012828 (X3)] 
	 * 
	 * Realiza o calculo do percentula de gordura, através do x1 que é a soma das dobras cutãneas abaixo 
	 * e x3 é a idade em anos.
	 */
	@Override
	Double equacaoMulher(AvaliacaoAntropometrica avaliacaoAntropometrica) {
		
		final DobrasCutaneas dobrasCutaneas = avaliacaoAntropometrica.getDobrasCutaneas();
//		de acordo com a formula x1 é a soma das dobras...
		final Double x1 = (
			dobrasCutaneas.getToracica()
			+ dobrasCutaneas.getAxilarMedia()
			+ dobrasCutaneas.getTricipal()
			+ dobrasCutaneas.getSubescapular()
			+ dobrasCutaneas.getAbdominal()
			+ dobrasCutaneas.getCoxa()
		);
		
//		[0,00046971 (X1) + 0,00000056 (X1)²] 
		final Double parte1 = (0.00046971 * x1) + ( 0.00000056 * ( x1 * x1 ) );
		
		final int idadeAnos = avaliacaoAntropometrica
				.getAvaliacaoFisica()
				.getPessoa()
				.getIdadeAnos();
		
//		[0,00012828 (X3)]
		final Double parte2 = 0.00012828 * idadeAnos;
		
//		DC = 1,0970 - [0,00046971 (X1) + 0,00000056 (X1)²] - [0,00012828 (X3)] 
		return 1.0970 - parte1 - parte2; 
		
	}

}
