package br.com.eits.boot.domain.entity.academia;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.eits.common.domain.entity.AbstractEntity;

@MappedSuperclass
public abstract class AbstractEntityAcademia extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1101853050405789833L;

	// ----------------------------- 
	// ----- ATRIBUTOS ------------- 
	// ----------------------------- 
	
	// Academia vinculada a entidade
	@ManyToOne(
		fetch = FetchType.LAZY,
		cascade = CascadeType.REFRESH,
		optional = false,
		targetEntity = Academia.class
	)
	private Academia academia;
	
	
	// ------------------------------------
	// ------- CONSTRUCTORS ---------------
	// ------------------------------------

	/**
	 * Constructor Default
	 */
	public AbstractEntityAcademia(){
		super();
	}
	
	/**
	 * Constructor com id 
	 * 
	 * @param id
	 */
	public AbstractEntityAcademia( Long id ){
		super(id);
	}

	/**
	 * Constructor com todos os atributos
	 * 
	 * @param academia
	 */
	public AbstractEntityAcademia(Academia academia) {
		super();
		this.academia = academia;
	}
	
}
