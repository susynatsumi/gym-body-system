package br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
//import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table
@Audited
@EqualsAndHashCode(callSuper = true)
@Data
@DataTransferObject
public class ProtocoloGuedes extends ProtocoloAvaliacaoAntropometrica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 902375995301369890L;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Genero genero;

	/**
	 * Guedes H-DC = 1,1714 - 0,0671 Log10 Triceps + suprailiaca + abdominal
	 * 
	 * M-DC = 1,1665 - 0,0706 Log10 coxaproximal + suprailiaca + subescapular
	 * 
	 * @param hdc
	 */
	public void equacaoHomem(BigDecimal hdc) {

		BigDecimal primeiroValor = new BigDecimal(1.1714);
		BigDecimal segundoValor = new BigDecimal(0.0671);
//		primeiroValor.divide(new Double(10), BigDecimal.ROUND_HALF_EVEN);
	}

	public void equacaoMulher(BigDecimal mdc) {

		BigDecimal primeiroValor = new BigDecimal(1.1665);
		BigDecimal segundoValor = new BigDecimal(0.0706);
	}
	
}
