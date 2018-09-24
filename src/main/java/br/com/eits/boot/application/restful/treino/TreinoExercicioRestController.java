package br.com.eits.boot.application.restful.treino;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;
import br.com.eits.boot.domain.service.academia.treino.TreinoExercicioService;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@RequestMapping("/api/treinos-exercicios")
@RestController
public class TreinoExercicioRestController {

	
	@Autowired
	private TreinoExercicioService treinoExercicioService;
	
	/**
	 * 
	 * Busca exercicios de um treino, de acordo com o id do treino 
	 * 
	 * @param idTreino
	 * @return
	 * 
	 */
	@GetMapping(
		value = "/treino-id/{idTreino}",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<TreinoExercicio>> getTreinosExerciciosByTreinoId(@PathVariable Long idTreino){
		
		try {
			
			Assert.notNull(
				idTreino,
				MessageSourceHolder.translate("service.object.id.not.null")
			);
			
			Page<TreinoExercicio> exericiciosTreino = this.treinoExercicioService.findTreinoExercicioByTreinoId(idTreino, null);
			
			return new ResponseEntity<Page<TreinoExercicio>>(exericiciosTreino , HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
		
	}
	
}
