package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@Data
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class ProtocoloPollock extends AvaliacaoAntropometrica {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4806703020602841395L;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Genero genero;
	
	public void equacaoHomem() {
		
	}
	
	public void equacaoMulher() {
		
	}
}
