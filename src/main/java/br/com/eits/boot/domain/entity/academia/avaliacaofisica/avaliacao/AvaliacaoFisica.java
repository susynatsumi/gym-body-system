package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.anamnese.Resposta;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AbstractEntityAvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table
@Audited
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class AvaliacaoFisica extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4175325433741291331L;
	
	/*-------------------------------------------------------------------
	 *				 	 ATRIBUTOS AVALIACAO FISICA
	 *-------------------------------------------------------------------*/
	
	// data de realização da avaliação física
	@NotNull
	@Column(nullable = false)
	private LocalDate data;
	
	// pessoa da qual foi realizada a avaliação fisica
	@ManyToOne(
		cascade = CascadeType.MERGE,
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = Pessoa.class
	)
	private Pessoa pessoa;
	
	// permetria realizada na avaliação fisica
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.EAGER,
//		mappedBy = "avaliacaoFisica",
//		optional = false,
		orphanRemoval = true
//		targetEntity = Perimetria.class
	)
	private Perimetria perimetria;

	// resposata da avaliacao fisica
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.EAGER,
//		mappedBy = "avaliacaoFisica",
//		optional = false,
		orphanRemoval = true
//		targetEntity = Resposta.class
	)
	private Resposta resposta;
	
	// referencia do protocolo de avaliacao antopometrica
	@NotNull
	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER,
			mappedBy = "avaliacaoFisica",
			optional = false,
			orphanRemoval = true,
			targetEntity = AbstractEntityAvaliacaoAntropometrica.class
		)
	private AbstractEntityAvaliacaoAntropometrica abstractEntityAvaliacaoAntropometrica;
	
}
