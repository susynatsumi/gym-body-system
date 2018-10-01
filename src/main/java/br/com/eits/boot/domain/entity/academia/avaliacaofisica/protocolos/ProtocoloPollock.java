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
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn
public class ProtocoloPollock extends AvaliacaoAntropometrica implements IProtocoloAvaliacaoAntropometrica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4806703020602841395L;

	/**
	 * De acordo com a fórumla 1,11200000 - [0,00043499 (X1) + 0,00000055 (X1)²] - [0,0002882 (X3)]
	 * onde x1 é a soma de algumas dobras e x3 é a idade em anos
	 * 
	 * retorna o percentual de gordura
	 */
	@Transient
	@Override
	public Double equacaoHomem() {
		
		final DobrasCutaneas dobrasCutaneas = this.getDobrasCutaneas();
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
		final int idadeAnos = getAvaliacaoFisica().getPessoa().getIdadeAnos();
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
	@Transient
	@Override
	public Double equacaoMulher() {
		
		final DobrasCutaneas dobrasCutaneas = this.getDobrasCutaneas();
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
		
		final int idadeAnos = getAvaliacaoFisica().getPessoa().getIdadeAnos();
//		[0,00012828 (X3)]
		final Double parte2 = 0.00012828 * idadeAnos;
		
//		DC = 1,0970 - [0,00046971 (X1) + 0,00000056 (X1)²] - [0,00012828 (X3)] 
		return 1.0970 - parte1 - parte2; 
		
	}

	/**
	 * Realiza o calculo da densidade corporal, validando se o sexo da pessoa avaliada é masculino ou feminino 
	 * e fazendo o calculo de acordo com a fórmula.
	 */
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
	
	// -----------------------------------------------------
	// ---------------------- CONSTRUCTORS -----------------
	// -----------------------------------------------------
	

	/**
	 * @param id
	 * @param dobrasCutaneas
	 * @param indiceMassaCorporal
	 * @param predicaoGorduraSiri
	 */
	public ProtocoloPollock(
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
	public ProtocoloPollock(Long id) {
		super(id);
	}

	/**
	 * Constructor default
	 */
	public ProtocoloPollock() {
	}
	
	
}
