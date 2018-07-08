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
public class OpcaoResposta extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2262334308593925329L;

	//TODO opcao de resposta do tipo string
	@NotNull
	@Column(nullable = false)
	private String texto;
}
