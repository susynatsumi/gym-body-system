package br.com.eits.boot.application.restful;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.application.configuration.jwt.TokenProvider;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.service.AccountService;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@RestController
public class ApiAuthController {

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	private JwtAuthenticationManager jwtAuthenticationManager;
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/api/teste")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void authenticate() {
		System.out.println("PASSOU");
	}

	/**
	 * Realiza login na api rest
	 * 
	 * @param pessoa
	 * @param httpServletResponse
	 * @return status ok e o token na response caso ocorra com sucesso, 
	 * 	UNAUTHORIZED caso os dados de login não sejam validos
	 */
	@PostMapping(
		value = "/api-login", 
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public ResponseEntity<String> loginApi(
		@RequestBody Pessoa pessoa, 
		HttpServletResponse httpServletResponse
	) {
		
		UsernamePasswordAuthenticationToken authenticationToken = 
		new UsernamePasswordAuthenticationToken(
			pessoa.getUsername(), 
			pessoa.getPassword()
		);
		
		try {
			
			Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
			// seta pessoa autenticada na sessão
//			jwtAuthenticationManager.setAuthentication(authentication);
			
			String token = tokenProvider.createToken(pessoa.getUsername());
			
			Pessoa pessoaLogada = (Pessoa) authentication.getPrincipal();
			
			Assert.notNull(pessoaLogada, MessageSourceHolder.translate("login.autenticate.bad.credentials"));
			
			pessoaLogada.setTokenJwt(token);
			
			accountService.updatePessoa(pessoaLogada);
			
			System.out.println(token);
			
			return new ResponseEntity<String>(token, HttpStatus.OK);
			
		} catch (AuthenticationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	/**
	 * Retorna pessoa logada na api 
	 * 
	 * @return
	 */
	@GetMapping(
		value="/api/pessoa-token",
		consumes= MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Pessoa> getPessoaByToken(@RequestParam("token") String token){
		System.out.println("Obtem pessoa logada");
		
		System.out.println("Token requerido "+ token);
//		Pessoa pessoaLogada = this.accountService.findPessoaById(1L);
		Pessoa pessoaLogada = this.accountService.findPessoaByToken(token);
		if(pessoaLogada != null){
			System.out.println("Obtem pessoa logada"+ pessoaLogada.getNome());
			
			return new ResponseEntity<Pessoa>(pessoaLogada, HttpStatus.OK);
		} 
		System.out.println("Obtem pessoa logada não encontrada");
		
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
	}
	
}
