package br.com.eits.boot.domain.entity.academia.notificacao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames =  {
		"pessoa_id", "notificacao_id"
	})
})
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class DestinatarioNotificacao extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4652998065722919520L;

	// Pessoa da notificação
	@NotNull
	@ManyToOne( 
		fetch = FetchType.EAGER,
		optional = false,
		cascade = CascadeType.REFRESH,
		targetEntity = Pessoa.class
	)
	private Pessoa pessoa;
	
	// notificacao 
	@NotNull
	@ManyToOne(
		fetch = FetchType.EAGER,
		optional = false,
		cascade = CascadeType.REFRESH,
		targetEntity = Notificacao.class
	)
	private Notificacao notificacao;
	
	
	// ------------------------------------------
	// ----------- CONSTRUCTORS -----------------
	// ------------------------------------------
	
	/**
	 * Constructor Default
	 */
	public DestinatarioNotificacao(){
		super();
	}
	
	/**
	 * Constructor com id
	 * @param id
	 */
	public DestinatarioNotificacao( Long id ){
		super(id);
	}

	/**
	 * Constructor com todos os parêmetros
	 * 
	 * @param pessoa
	 * @param notificacao
	 */
	public DestinatarioNotificacao( 
		Pessoa pessoa, 
		Notificacao notificacao
	) {
		super();
		this.pessoa = pessoa;
		this.notificacao = notificacao;
	}
	
}
