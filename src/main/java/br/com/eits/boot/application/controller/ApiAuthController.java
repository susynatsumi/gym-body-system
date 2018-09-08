package br.com.eits.boot.application.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.eits.boot.application.configuration.jwt.TokenProvider;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.service.AccountService;

@RestController
public class ApiAuthController {

//	@Autowired
//	private AccountService accountService;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/api/teste")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void authenticate() {
		System.out.println("PASSOU");
	}

	@PostMapping(name = "/api-login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String loginApi(@RequestBody Pessoa pessoa, HttpServletResponse httpServletResponse) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				pessoa.getUsername(), pessoa.getPassword());

		try {
			this.authenticationManager.authenticate(authenticationToken);
			return tokenProvider.createToken(pessoa.getUsername());
		} catch (AuthenticationException e) {
			e.printStackTrace();
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
	}

}
