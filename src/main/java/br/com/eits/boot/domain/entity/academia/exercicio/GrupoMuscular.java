package br.com.eits.boot.domain.entity.academia.exercicio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.EqualsAndHashCode;

import lombok.Data;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class GrupoMuscular extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3495044216297390160L;

	// ------------------------------------
	// ----------------- ATRUBUTOS --------
	// ------------------------------------
	
	// nome do grupo muscular
	@NotEmpty
	@Column( nullable = false, length = 50 , unique = true)
	private String nome;
	
	// descricao do grupo muscular, texto longo
	@Column( nullable = true, length = 255)
	private String descricao;

	// ----------------------------------
	// ------ CONSTRUCTORS --------------
	// ----------------------------------
	
	/**
	 * Constructor default
	 */
	public GrupoMuscular(){
		super();
	}
	
	/**
	 * Constructor com id
	 * @param id
	 */
	public GrupoMuscular( Long id ){
		super(id);
	}

	/**
	 * Constructor com todos os fields
	 * 
	 * @param nome
	 * @param descricao
	 * 
	 */
	public GrupoMuscular(String nome, String descricao) {
		super();
		this.nome = nome;
		this.descricao = descricao;
	}
	
}
