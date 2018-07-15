package br.com.eits.boot.application.restful;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import br.com.eits.boot.domain.entity.account.Pessoa;
//import br.com.eits.boot.domain.service.PessoaService;

@RequestMapping("/api/pessoas")
@RestController
public class PessoaRestController {

//	@Autowired
//	private PessoaService pessoaService; 
//	
//	@GetMapping("")
//	public ResponseEntity<List<Pessoa>> listPessoas(){
//		return new ResponseEntity<>(this.pessoaService.listarTodos(), HttpStatus.ACCEPTED);
//	}
//	
//	@GetMapping("login/{login}")
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
