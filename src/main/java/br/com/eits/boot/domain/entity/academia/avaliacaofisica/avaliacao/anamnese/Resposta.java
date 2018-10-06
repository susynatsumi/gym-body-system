package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.anamnese;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@DataTransferObject
@EqualsAndHashCode( callSuper = true )
@Data
public class Resposta extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8262376995559497384L;

	// ------------------------------------------
	// ----------- ATRIBUTOS --------------------
	// ------------------------------------------
	
	// resposta da primeira perguta
	@NotEmpty
	@Column( nullable = false )
	private String objetivosAtividadeFisica;
	
	// resposta da segunda pergunta
	@NotEmpty
	@Column( nullable = false )
	private String praticaAtividade;
	
	// resposta da terceira pergunta
	@NotEmpty
	@Column( nullable = false )
	private String medicamento;
	
	// resposta da quarta pergutna
	@NotEmpty
	@Column( nullable = false )
	private String cirurgia;
	
	// resposta da sexta pergunta
	@NotEmpty
	@Column( nullable = false )
	private String doencaFamiliar; 
	
	// resposta da setima pergunta
	@NotEmpty
	@Column( nullable = false )
	private String observacao;

	// referencia da avaliacao fisica
//	@OneToOne(
//		cascade = CascadeType.MERGE,
//		fetch = FetchType.LAZY,
//		mappedBy = "resposta",
//		optional = false,
//		orphanRemoval = false,
//		targetEntity = AvaliacaoFisica.class
//	) 
//	private AvaliacaoFisica avaliacaoFisica;
	
	
	// -----------------------------------
	// ----------CONSTRUCTOR -------------
	// -----------------------------------

	/**
	 * 
	 * @param id
	 */
	public Resposta( Long id ) {
		super( id );
	}
	
	/**
	 * Constructor default
	 */
	public Resposta() {
	}

	/**
	 * @param id
	 * @param objetivosAtividadeFisica
	 * @param praticaAtividade
	 * @param medicamento
	 * @param cirurgia
	 * @param doencaFamiliar
	 * @param observacao
	 * @param avaliacaoFisica
	 */
	public Resposta(
		Long id, 
		String objetivosAtividadeFisica, 
		String praticaAtividade, 
		String medicamento,
		String cirurgia, 
		String doencaFamiliar, 
		String observacao
//		, AvaliacaoFisica avaliacaoFisica
	) {
		super(id);
		this.objetivosAtividadeFisica = objetivosAtividadeFisica;
		this.praticaAtividade = praticaAtividade;
		this.medicamento = medicamento;
		this.cirurgia = cirurgia;
		this.doencaFamiliar = doencaFamiliar;
		this.observacao = observacao;
//		this.avaliacaoFisica = avaliacaoFisica;
	}

}
