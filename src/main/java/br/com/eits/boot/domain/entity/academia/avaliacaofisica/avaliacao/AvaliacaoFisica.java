package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.anamnese.Resposta;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(uniqueConstraints={
	@UniqueConstraint(columnNames={"data", "pessoa_id"})
})
@Audited
@DataTransferObject
@EqualsAndHashCode(callSuper = true, exclude = "avaliacaoAntropometrica")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=AvaliacaoFisica.class)
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
	@Column(nullable = false, updatable = false )
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
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
//		mappedBy = "avaliacaoFisica",
		optional = false,
		orphanRemoval = true,
		targetEntity = Perimetria.class
	)
	private Perimetria perimetria;

	// resposata da avaliacao fisica
	@NotNull
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
//		mappedBy = "avaliacaoFisica",
		optional = false,
		orphanRemoval = true,
		targetEntity = Resposta.class
	)
	private Resposta resposta;
	
	// referencia do protocolo de avaliacao antopometrica
	@OneToOne(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
//			mappedBy = "avaliacaoFisica",
		optional = false,
		orphanRemoval = true,
		targetEntity = AvaliacaoAntropometrica.class
	)
	private AvaliacaoAntropometrica avaliacaoAntropometrica;


	// --------------------------------------------------
	// --------------------- CONSTRUCTOR ----------------
	// --------------------------------------------------

	/**
	 * @param id
	 * @param data
	 * @param pessoa
	 * @param perimetria
	 * @param resposta
	 * @param avaliacaoAntropometrica
	 */
	public AvaliacaoFisica(
		Long id, 
		LocalDate data, 
		Pessoa pessoa, 
		Perimetria perimetria,
		Resposta resposta, 
		AvaliacaoAntropometrica avaliacaoAntropometrica
	) {
		super(id);
		this.data = data;
		this.pessoa = pessoa;
		this.perimetria = perimetria;
		this.resposta = resposta;
		this.avaliacaoAntropometrica = avaliacaoAntropometrica;
	}
	
	/**
	 * 
	 * @param id
	 */
	public AvaliacaoFisica( Long id ) {
		super(id);
	}
	
	/**
	 * Constructor defaults
	 */
	public AvaliacaoFisica() {
	}
}
