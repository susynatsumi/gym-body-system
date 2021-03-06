package br.com.eits.boot.application.restful;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.service.AccountService;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@RequestMapping(value = "/api/pessoas")
@RestController
public class PessoaRestController {

	@Autowired
	private AccountService pessoaService; 
	

	/**
	 * 
	 * Altara a senha de uma pessoa através de seu login 
	 * 
	 * @param pessoa
	 * @return
	 */
	@PostMapping(
		value = "/alterar-senha",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public ResponseEntity<String> alterarSenha(
		@RequestBody Pessoa pessoa
	){
		try{
			
			Assert.notNull(
				pessoa, 
				MessageSourceHolder.translate("userService.alterar.senha.dados.invalidos")
			);
			
			this.pessoaService.alterarSenha(
				pessoa.getId(), 
				pessoa.getSenha(), 
				pessoa.getSenhaAntiga()
			);
			
			return new ResponseEntity<String>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 
	 * Realiza update de uma pessoa
	 * 
	 * @param pessoa
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PutMapping(
		value = "",
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity updatePessoa( @RequestBody Pessoa pessoa) {

		try {
			
			pessoa = this.pessoaService.updatePessoa(pessoa);
			
			return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
		}
		
	}
	
	/**
	 * 
	 * Realiza busca de alunos ativos por nome
	 *  
	 * @param nome
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(
		value = {
			"/alunos/{nome}/",
			"/alunos/"
		},
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity findAlunosByNome(
		@PathVariable(required = false) String nome
	){

		try {
			System.out.println("Filtrando por: "+nome);
			final Sort sort = Sort.by(Direction.ASC, "nome");
			
			final PageRequest pageRequest = PageRequest.of(0, 30, sort);
			
			final Page<Pessoa> alunos = this.pessoaService
					.listByFilters(
						nome, 
						true, 
						true, 
						pageRequest
					);
			
			return new ResponseEntity<Page<Pessoa>>(alunos, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	/**
	 * 
	 * Realiza busca de alunos ativos por nome
	 *  
	 * @param nome
	 * @return
	 */
	@GetMapping(
		value = "/{filter}/filters",
		produces= MediaType.APPLICATION_JSON_VALUE
	)
	@SuppressWarnings("rawtypes")
	public ResponseEntity findPessoasByFilters(@PathVariable String filter){

		try {
			
			final Sort sort = Sort.by(Direction.ASC, "nome");
			
			final PageRequest pageRequest = PageRequest.of(0, 30, sort);
			
			final Page<Pessoa> alunos = this.pessoaService
					.listByFilters(
						filter, 
						true, 
						false, 
						pageRequest
					);
			
			return new ResponseEntity<Page<Pessoa>>(alunos, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
		}
		
		return null;
	}
	
	// ---------------------------------- avaliacao fisica ----------------------------------------
	
	
	/**
	 * Busca lista as avaliacoes fisicas pelos filters
	 * 
	 * @param filters
	 * @param dataInicio
	 * @param dataFim
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(
		value = {
			"/avaliacao-fisica//by-filters/{filters}/pessoa-id/{idPessoa}/inicio/{dataInicio}/fim/{dataFim}/",
			"/avaliacao-fisica//by-filters/pessoa-id/{idPessoa}/inicio/{dataInicio}/fim/{dataFim}/",
			"/avaliacao-fisica//by-filters/{filters}/inicio/{dataInicio}/fim/{dataFim}/",
			"/avaliacao-fisica//by-filters/inicio/{dataInicio}/fim/{dataFim}/"
		},
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity listAvaliacoesFisicasByFilters(
		@PathVariable(required = false) String filters,
		@PathVariable(required = false) Long idPessoa,
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicio,
		@PathVariable @DateTimeFormat(iso = ISO.DATE) LocalDate dataFim
	){
		
		try{
			
			
			List<Sort.Order> orders = Arrays
					.asList(
						new Sort.Order(Direction.DESC, "avaliacaoFisica.data"),
						new Sort.Order(Direction.ASC, "nome")
					);
			
			Page<Pessoa> avaliacoes = this.pessoaService
					.listAvaliacaoFisicaByFilters(
						filters,
						idPessoa,
						dataInicio, 
						dataFim, 
						PageRequest.of(0, 10, Sort.by(orders))
					);
			
			return new ResponseEntity<Page<Pessoa>>(avaliacoes, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
}
