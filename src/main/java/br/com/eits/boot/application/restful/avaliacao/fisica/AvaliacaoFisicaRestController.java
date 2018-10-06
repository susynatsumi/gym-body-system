package br.com.eits.boot.application.restful.avaliacao.fisica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.AvaliacaoFisicaService;


@RequestMapping("/api/avaliacao-fisica")
@RestController
public class AvaliacaoFisicaRestController {

	// -- ---------------------------------------------------
	// -- --------------------- SERVICES --------------------
	// -- ---------------------------------------------------
	
	
	@Autowired
	private AvaliacaoFisicaService avaliacaoFisicaService;
	
	
	// ---------------------------------------------------------
	// ------------------ MÉTODOS ------------------------------
	// ---------------------------------------------------------
	
	/**
	 * 
	 * Salva uma avaliacao fisica através de uma service
	 * 
	 * @param avaliacaoFisica
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping(
		value= {"/",""},
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity salvarAvaliacaoFisica(@RequestBody AvaliacaoFisica avaliacaoFisica){
		
		try {
			
			AvaliacaoFisica avaliacaoFisicaSaved = this
					.avaliacaoFisicaService
					.insertAvaliacaoFisica(
						avaliacaoFisica
					);
			
			return new ResponseEntity<AvaliacaoFisica>(
				avaliacaoFisicaSaved, 
				HttpStatus.OK
			);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return new ResponseEntity<String>(
				e.getMessage(),
				HttpStatus.BAD_REQUEST
			);
			
		}
		
	}
	
	
}
