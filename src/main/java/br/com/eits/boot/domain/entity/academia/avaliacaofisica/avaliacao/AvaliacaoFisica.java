package br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao;

import java.time.LocalDate;

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
@Data
@Table
@Audited
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class AvaliacaoFisica extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4175325433741291331L;
	
	/*-------------------------------------------------------------------
	 *				 	 ATRIBUTOS AVALIACAO FISICA
	 *-------------------------------------------------------------------*/
	
	@NotNull
	@Column(nullable = false)
	private LocalDate data;
	
}
