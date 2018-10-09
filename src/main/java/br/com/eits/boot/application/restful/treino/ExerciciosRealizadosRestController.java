package br.com.eits.boot.application.restful.treino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioRealizado;
import br.com.eits.boot.domain.service.academia.treino.ExercicioRealizadoService;

@SuppressWarnings({"rawtypes"})
@RequestMapping("/api/exercicios-realizados")
@RestController
public class ExerciciosRealizadosRestController {

	
	@Autowired
	private ExercicioRealizadoService exercicioRealizadoService;
	
	@GetMapping(
		value = "/treino-data-id/{idTreinoData}/",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity getExerciciosRealizadosByTreinoDataId(
		@PathVariable Long idTreinoData
	){
		
		try {
			
			Sort sort = new Sort(Direction.ASC, "treinoExercicio.exercicio.nome");
			PageRequest.of(0, 100, sort);
			
			final Page<ExercicioRealizado> exerciciosRealizados = this.exercicioRealizadoService
					.findExercicioRealizadoByTreinoDataId(
						idTreinoData,
						null
					);
			
			return new ResponseEntity<Page<ExercicioRealizado>>(exerciciosRealizados, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		
		
	}
	
}
