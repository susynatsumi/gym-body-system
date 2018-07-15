package br.com.eits.boot.domain.entity.academia.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.Academia;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {"pessoa_id", "academia_id"})
})
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class PessoaAcademia extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7626513570124448372L;
	
	// se e pessoa está ativa para acessar dados de determinada academia
	@Column(nullable = false)
	private Boolean isAtivo;
	
	// pessoa que tem vinculo com uma academia
	@ManyToOne(
		optional = false,
		targetEntity = Pessoa.class,
		fetch = FetchType.EAGER,
		cascade = CascadeType.MERGE
	)
	private Pessoa pessoa;
	
	// academia na qual a pessoa tem vinculo
	@ManyToOne(
		optional = false,
		targetEntity = Academia.class,
		fetch = FetchType.EAGER,
		cascade = CascadeType.MERGE
	)
	private Academia academia;

	// -----------------------------------
	// --------  CONSTRUCTORS ------------
	// -----------------------------------
	
	/**
	 * constructor default
	 */
	public PessoaAcademia() {
		super();
	}
	
	/**
	 * constructor com id 
	 * @param id
	 */
	public PessoaAcademia( Long id ){
		super(id);
	}
	
	/**
	 * Constructor com todos os atributos 
	 * 
	 * @param isAtivo
	 * @param pessoa
	 * @param academia
	 * 
	 */
	public PessoaAcademia(Boolean isAtivo, Pessoa pessoa, Academia academia) {
		super();
		this.isAtivo = isAtivo;
		this.pessoa = pessoa;
		this.academia = academia;
	}
	
	
	
}
