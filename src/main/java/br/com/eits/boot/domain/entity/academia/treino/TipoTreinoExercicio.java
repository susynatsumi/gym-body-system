package br.com.eits.boot.domain.entity.academia.treino;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject(type = "enum")
public enum TipoTreinoExercicio {

	// ----------------------------------------------------------------
	// --------- FORMA EM QUE O EXERCICIO DEVE SER FEITO NO TREINO ----
	// ----------------------------------------------------------------
	
	CARGA_REPETICOES,//0
	TEMPO,//1
	REPETICOES;//2
	
}
