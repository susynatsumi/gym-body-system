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
@Data
@Audited
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class Pergunta extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2933216760989005583L;
	
	/*-------------------------------------------------------------------
	 *				 		  ATRIBUTOS PERGUNTA
	 *-------------------------------------------------------------------*/

	@NotNull
	@Column(nullable = false)
	private Integer ordem; //ordenação das perguntas na tela, manter a sequencia na ordem necessária
	
	@NotNull
	@Column(nullable = false)
	private String pergunta; //para pergunta
	
	@NotNull
	@Column(nullable = false)
	private Boolean obrigatorio; //obrigatório
}
