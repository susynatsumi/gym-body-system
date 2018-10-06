package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
import br.com.eits.boot.domain.entity.account.Pessoa;
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
	public Double equacaoHomem() {
		
		DobrasCutaneas dobrasCutaneas = this.getDobrasCutaneas();
		
		Double soma = (
			dobrasCutaneas.getTricipal() 
			+ dobrasCutaneas.getSupraIliaca() 
			+ dobrasCutaneas.getAbdominal()
		);
		
		Double log = Math.log10(soma);
		
		return ( 1.1714d - ( 0.0671d * log ) ); 
	}

	@Transient
	@Override
	public Double equacaoMulher() {
		
		DobrasCutaneas dobrasCutaneas = this.getDobrasCutaneas();
		
		Double soma = (
			dobrasCutaneas.getCoxa()
			+ dobrasCutaneas.getSupraIliaca()
			+ dobrasCutaneas.getSubescapular()
		);
			
		Double log = Math.log10(soma);
		
		return ( 1.1665d - ( 0.0706d * log ) );
	}
	
	@Override
	public Double realizaCalculoPercentualGordura() {
		
		Assert.notNull(
			this.getDobrasCutaneas(),
			"protocolo.dobras.cutaneas.null"
		);
		
		Assert.notNull(
			this.getAvaliacaoFisica(),
			"protocolo.avaliacao.fisica.null"
		);
		
		Assert.notNull(
			this.getAvaliacaoFisica().getPessoa(),
			"protocolo.avaliacao.fisica.null"
		);

		final Pessoa aluno = this.getAvaliacaoFisica().getPessoa();
		
		if(Genero.FEMININO.equals(aluno.getGenero())){
			return this.equacaoMulher();
		}
		
		return this.equacaoHomem();
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
		PredicaoGorduraSiri predicaoGorduraSiri,
		Double densidadeCorporal
	) {
		super(id, dobrasCutaneas, indiceMassaCorporal, predicaoGorduraSiri, densidadeCorporal);
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
