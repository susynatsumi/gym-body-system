package br.com.eits.boot.domain.entity.academia.treino;

import org.directwebremoting.annotations.DataTransferObject;

import lombok.Getter;

@DataTransferObject(type = "enum")
public enum DiaSemana {

	// ------------------------------------------------------------
	// ----- DIA DA SEMANA PARA REALIZACAO DO TREINO --------------
	// ------------------------------------------------------------
	/**
	 * dia da semana para comparaçao com localDate
	 */
	DOMINGO("SUNDAY"), //0
	SEGUNDA("MONDAY"), //1
	TERCA("TUESDAY"), //2
	QUARTA("WEDNESDAY"),//3
	QUINTA("THURSDAY"),//4
	SEXTA("FRIDAY"),//5
	SABADO("SATURDAY");//6

	@Getter
	private final String dayOfWeek;
	
	private DiaSemana(String dayOfWeek){
		this.dayOfWeek = dayOfWeek;
	}
	
	
	
}
