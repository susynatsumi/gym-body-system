package br.com.eits.boot.domain.entity.academia.treino;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum DiaSemana {

	// ------------------------------------------------------------
	// ----- DIA DA SEMANA PARA REALIZACAO DO TREINO --------------
	// ------------------------------------------------------------
	
	DOMINGO, //0
	SEGUNDA, //1
	TERCA, //2
	QUARTA,//3
	QUINTA,//4
	SEXTA,//5
	SABADO;//6
	
}
