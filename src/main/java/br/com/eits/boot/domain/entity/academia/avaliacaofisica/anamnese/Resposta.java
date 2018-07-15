package br.com.eits.boot.domain.entity.academia.avaliacaofisica.anamnese;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
public class Resposta extends AbstractEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6156397687732144310L;

	@NotNull
	@Column(nullable = false)
	private String texto;
	/**
	 * @ManyToOne(
			fetch = FetchType.LAZY,
			targetEntity = Pergunta.class,
			optional = false  //não pode ser nula
	)
	 */
	@ManyToOne(
			fetch = FetchType.LAZY,
			targetEntity = OpcaoResposta.class,
			optional = false
	)
	private OpcaoResposta opcaoResposta;
	
	@ManyToOne(
			fetch = FetchType.LAZY,
			targetEntity = Pergunta.class,
			optional = false
	)
	private Pergunta pergunta;
}
