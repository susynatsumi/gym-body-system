package br.com.eits.boot.domain.entity.academia.avaliacaofisica.anamnese;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum TipoPergunta {
	
	/*-------------------------------------------------------------------
	 *				   ENUM TIPO PERGUNTA PARA ANAMNESE
	 *-------------------------------------------------------------------*/
	
	DIAGNOSTICO,
	HISTORICO_MEDICO,
	QUEIXA;
}
