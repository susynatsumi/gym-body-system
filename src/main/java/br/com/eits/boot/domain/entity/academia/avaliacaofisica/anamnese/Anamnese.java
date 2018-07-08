package br.com.eits.boot.domain.entity.academia.avaliacaofisica.anamnese;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class Anamnese extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4785949470919116329L;
	
	/*-------------------------------------------------------------------
	 *				 		  ATRIBUTOS ANAMNESE
	 *-------------------------------------------------------------------*/

	@NotNull
	@Column(nullable = false)
	private String nome; //nome
	
	@NotNull
	@Column(nullable = false)
	private Boolean isAtivo; //status
	
}
