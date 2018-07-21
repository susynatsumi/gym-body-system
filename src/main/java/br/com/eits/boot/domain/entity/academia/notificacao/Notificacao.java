package br.com.eits.boot.domain.entity.academia.notificacao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class Notificacao extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8567831806672665141L;

	// titulo da notificacao
	@NotEmpty
	@Column( nullable = false , length = 70 )
	private String titulo;
	
	// texto da notificacao 
	@NotEmpty
	@Column(nullable = false)
	private String texto;
	
	// treino de referencia 
	@ManyToOne(
		fetch = FetchType.LAZY,
		optional = true,
		targetEntity = TreinoData.class
	)
	private TreinoData treinoData;
	
	
	@OneToMany( 
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "notificacao",
		orphanRemoval = true,
		targetEntity = PessoaNotificacao.class
	)
	private List<PessoaNotificacao> pessoasNotificacoes;
	// ----------------------------------
	// ------ CONSTRUTORES --------------
	// ----------------------------------
	
	/**
	 * Constructor default
	 */
	public Notificacao(){
		super();
	}
	
	/**
	 * Constructor com paramentro id 
	 * 
	 * @param id
	 */
	public Notificacao( Long id ){
		super(id);
	}

	/**
	 * Constructor com todos os parametros 
	 * 
	 * @param titulo
	 * @param texto
	 * @param treinoData
	 * 
	 */
	public Notificacao(
		String titulo, 
		String texto, 
		TreinoData treinoData,
		List<PessoaNotificacao> pessoasNotificacoes
	) {
		super();
		this.titulo = titulo;
		this.texto = texto;
		this.treinoData = treinoData;
		this.pessoasNotificacoes = pessoasNotificacoes;
	}
	
	
}