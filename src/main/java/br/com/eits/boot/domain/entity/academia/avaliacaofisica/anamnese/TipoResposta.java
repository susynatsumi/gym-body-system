package br.com.eits.boot.domain.entity.academia.avaliacaofisica.anamnese;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum TipoResposta {
	
	/*-------------------------------------------------------------------
	 *				   ENUM TIPO RESPOSTA PARA ANAMNESE
	 *-------------------------------------------------------------------*/

	MULTIPLA_ESCOLHA,
	TEXTO;
}
