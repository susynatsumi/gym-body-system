package br.com.eits.boot.application.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
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
	
}
