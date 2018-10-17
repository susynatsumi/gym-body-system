package br.com.eits.boot.application.restful.avaliacao.fisica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.AvaliacaoFisicaService;

@SuppressWarnings("rawtypes")
@RequestMapping(value = "/api/avaliacao-fisica")
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
	@PostMapping(
		value= {"/",""},
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity salvarAvaliacaoFisica(@RequestBody AvaliacaoFisica avaliacaoFisica){
		
		try {
			
			AvaliacaoFisica avaliacaoFisicaSaved = null;
			
			avaliacaoFisicaSaved = this
					.avaliacaoFisicaService
					.salvarAvaliacaoFisica(
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
	
	
//	/**
//	 * Busca lista as avaliacoes fisicas pelos filters
//	 * 
//	 * @param filters
//	 * @param dataInicio
//	 * @param dataFim
//	 * @return
//	 */
//	@GetMapping(
//		value = {
//			"/by-filters/{filters}/pessoa-id/{idPessoa}/inicio/{dataInicio}/fim/{dataFim}/",
//			"/by-filters/pessoa-id/{idPessoa}/inicio/{dataInicio}/fim/{dataFim}/",
//			"/by-filters/{filters}/inicio/{dataInicio}/fim/{dataFim}/",
//			"/by-filters/inicio/{dataInicio}/fim/{dataFim}/"
//		},
//		produces = MediaType.APPLICATION_JSON_VALUE
//	)
//	public ResponseEntity listAvaliacoesFisicasByFilters(
//		@PathVariable(required = false) String filters,
//		@PathVariable(required = false) Long idPessoa,
//		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicio,
//		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataFim
//	){
//		
//		try{
//			
//			Sort sort = new Sort(Direction.ASC, "data");
//			PageRequest pageRequest = PageRequest.of(0, 10, sort);
//			
//			Page<AvaliacaoFisica> avaliacoes = this.avaliacaoFisicaService
//					.listAvaliacaoFisicaByFilters(
//						filters,
//						idPessoa,
//						dataInicio, 
//						dataFim, 
//						pageRequest
//					);
//			
//			return new ResponseEntity<Page<AvaliacaoFisica>>(avaliacoes, HttpStatus.OK);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND);
//		
//	}
	
	
	/**
	 * 
	 * Busca lista as avaliacoes fisicas pelos filters
	 * 
	 * @param idPessoa
	 * @return
	 */
	@GetMapping(
		value = "/{idPessoa}/pessoa-id",
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity listAvaliacoesFisicasByPessoaId(@PathVariable Long idPessoa){
		
		try{
			Sort sort = new Sort(Direction.ASC, "data");
			PageRequest pageRequest = PageRequest.of(0, 10, sort);
			
			this.avaliacaoFisicaService.listAvalicaoFisicaByPessoaId(idPessoa, pageRequest);
			
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND);
		
	}
	
	@GetMapping(
		value = {
			"/{idAvaliacaoFisica}/",
			"/{idAvaliacaoFisica}"
		},
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity findAvaliacaoFisicaById(
		@PathVariable Long idAvaliacaoFisica
	){
		try {
			
			final AvaliacaoFisica avaliacaoFisica = this.avaliacaoFisicaService
					.findAvaliacaoFisicaById(
						idAvaliacaoFisica
					);
			
			return new ResponseEntity<AvaliacaoFisica>(avaliacaoFisica, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND);
	}
}
