package br.com.eits.boot.application.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.domain.service.AccountService;

@RequestMapping("/api/pessoas")
@RestController
public class PessoaRestController {

	@Autowired
	private AccountService pessoaService; 

//	public ResponseEntity<Pessoa> findByLogin( @PathVariable("login") String login ){
//	
//		final Pessoa pessoa = this.pessoaService.findPessoaByLogin(login);
//		
//		if(pessoa == null){
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		
//		return new ResponseEntity<Pessoa>(pessoa, HttpStatus.OK);
//		
//	}
//	
}
