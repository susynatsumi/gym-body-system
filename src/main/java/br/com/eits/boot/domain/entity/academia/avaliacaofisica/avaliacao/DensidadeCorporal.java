package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Data
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
@Audited
public class DensidadeCorporal extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450595812525490983L;
	
	/*-------------------------------------------------------------------
	 *				 	 ATRIBUTOS DENSIDADE CORPORAL
	 *-------------------------------------------------------------------*/

	private BigDecimal porcentagem;
	
	private BigDecimal somaTotal;
	
	private BigDecimal taxaMagra;
	
	private BigDecimal taxaGorda;
}
