package br.com.eits.boot.application.restful.avaliacao.fisica;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
	
	/**
	 * 
	 * Busca lista as avaliacoes fisicas pelos filters
	 * 
	 * @param idPessoa
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(
		value = {
			"/by-filters/{filters}/inicio/{dataInicio}/fim/{dataFim}/",
			"/by-filters/{dataInicio}/inicio/{dataFim}/fim",
			"/by-filters/{dataInicio}/inicio/",
			"/by-filters/{dataFim}/fim",
			"/{filters}/by-filters/"
		},
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity listAvaliacoesFisicasByFilters(
		@PathVariable String filters,
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicio,
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataFim
	){
		
		try{
			
			Sort sort = new Sort(Direction.ASC, "data");
			PageRequest pageRequest = PageRequest.of(0, 10, sort);
			
			Page<AvaliacaoFisica> avaliacoes = this.avaliacaoFisicaService
					.listAvaliacaoFisicaByFilters(
							filters,dataInicio, dataFim, pageRequest
					);
			
			return new ResponseEntity<Page<AvaliacaoFisica>>(avaliacoes, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return null;
		
	}
	
	
	/**
	 * 
	 * Busca lista as avaliacoes fisicas pelos filters
	 * 
	 * @param idPessoa
	 * @return
	 */
	@SuppressWarnings("rawtypes")
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
		return null;
		
	}
	
}
