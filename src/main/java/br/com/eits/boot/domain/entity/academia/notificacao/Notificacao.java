package br.com.eits.boot.domain.entity.academia.notificacao;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=Notificacao.class)
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
	
	@OneToMany( 
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "notificacao",
		orphanRemoval = true,
		targetEntity = DestinatarioNotificacao.class
	)
	private List<DestinatarioNotificacao> destinatarioNotificacoes;
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
	 * 
	 */
	public Notificacao(
		String titulo, 
		String texto, 
		List<DestinatarioNotificacao> destinatarioNotificacoes
	) {
		super();
		this.titulo = titulo;
		this.texto = texto;
		this.destinatarioNotificacoes = destinatarioNotificacoes;
	}
	
	
}
