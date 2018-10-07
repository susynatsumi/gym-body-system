package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.calculos;

import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
import br.com.eits.boot.domain.entity.account.Pessoa;

public abstract class AbstractCalculoProtocoloAvaliacaoFisica {

	abstract Double equacaoHomem(AvaliacaoAntropometrica avaliacaoAntropometrica);
	
	abstract Double equacaoMulher(AvaliacaoAntropometrica avaliacaoAntropometrica);
	
	public Double realizaCaluloPercentualGordura(AvaliacaoAntropometrica avaliacaoAntropometrica){
		
		Assert.notNull(
			avaliacaoAntropometrica.getTipoProtocolo(),
			"protocolo.tipo.null"
		);
		
		Assert.notNull(
			avaliacaoAntropometrica.getDobrasCutaneas(),
			"protocolo.dobras.cutaneas.null"
		);
		
		Assert.notNull(
				avaliacaoAntropometrica.getAvaliacaoFisica(),
			"protocolo.avaliacao.fisica.null"
		);
		
		Assert.notNull(
				avaliacaoAntropometrica.getAvaliacaoFisica().getPessoa(),
			"protocolo.avaliacao.fisica.null"
		);

		final Pessoa aluno = avaliacaoAntropometrica.getAvaliacaoFisica().getPessoa();
		
		if(Genero.FEMININO.equals(aluno.getGenero())){
			return this.equacaoMulher(avaliacaoAntropometrica);
		}
		
		return this.equacaoHomem(avaliacaoAntropometrica);
		
	}
	
	
	
	
}
