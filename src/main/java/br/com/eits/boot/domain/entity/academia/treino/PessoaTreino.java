package br.com.eits.boot.domain.entity.academia.treino;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(uniqueConstraints = {
	@UniqueConstraint( columnNames = {"pessoa_id", "papel", "treino_id" } ),
	@UniqueConstraint( columnNames = {"treino_id", "papel"})
})
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class PessoaTreino extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2030176060914785165L;

	// --------------------------------------
	// ---------- ATRIBUTOS -----------------
	// --------------------------------------
	
	// Pessoa que terá vinculo com o treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.EAGER,
		optional = false,
		targetEntity = Pessoa.class
	)
	private Pessoa pessoa;
	
	// treino vinculado a pessoa
	@NotNull
	@ManyToOne(
		fetch = FetchType.EAGER,
		optional = false,
		targetEntity = Treino.class
	)
	private Treino treino;
	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column( nullable = false )
	private Papel papel;
	
	
	//--------------------------------------------------------
	//-------------  CONSTRUCTORS ----------------------------
	//--------------------------------------------------------
	
	/**
	 * Constructor Default
	 */
	public PessoaTreino(){
		super();
	}
	
	/**
	 * Constructor com Id
	 * 
	 * @param id
	 */
	public PessoaTreino( Long id ){
		super(id);
	}

	public PessoaTreino(
		Pessoa pessoa, 
		Treino treino,
		Papel papel
	) {
		super();
		this.pessoa = pessoa;
		this.treino = treino;
		this.papel = papel;
	}
	
	
	
}
