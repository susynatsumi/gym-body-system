package br.com.eits.boot.application.configuration.jwt;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {
//	usando o padrão novo, com o Key
//	private final String secretKey;

	private final long tokenValidityInMilliseconds;

	private final UserDetailsService userService;
	
	private Key key;

	private Key getKey(){
		if(key == null){
			key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		}
		return key;
	}
	
	/**
	 * 
	 * @param config
	 * @param userService
	 */
	public TokenProvider(AppConfig config, UserDetailsService userService) {
//		this.secretKey = Base64.getEncoder().encodeToString(config.getSecret().getBytes());
		this.tokenValidityInMilliseconds = 1000 * config.getTokenValidityInSeconds();
		this.userService = userService;
	}

	/**
	 * Cria um token com base na chave que está configurada no yml propriedade
	 * Secret e com base no tempo de validade do token, também configurado lá,
	 * na propriedade token-validity-in-seconds
	 * 
	 * @param username
	 * @return
	 */
	public String createToken(String username) {
		Date now = new Date();
		Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

		return Jwts.builder().setId(UUID.randomUUID().toString())
				.setSubject(username)
				// setIssuedAt recebe a data atual
				.setIssuedAt(now)
//				.signWith(alg, base64EncodedSecretKey)
				.signWith(getKey())
				// recebe a chave e o algoritmo
//				.signWith(SignatureAlgorithm.HS512, this.secretKey)
				// data de validade do token
				.setExpiration(validity).compact();
	}

	/**
	 * Valida o token, com base no secret, extrai o nome do usuário da
	 * requisição e busca o user no banco de dados
	 * 
	 * 
	 * é necessário que a chave secret não tenha sido alterada após o usuário
	 * ter adquirido um token em createToken
	 * 
	 * @param token
	 * @return
	 */
	public Authentication getAuthentication(String token) {
		String username = Jwts.parser().setSigningKey(getKey()).parseClaimsJws(token).getBody().getSubject();
		UserDetails userDetails = this.userService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

}