package br.com.eits.boot.application.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Eduardo
 *
 */
@Component
public class JwtAuthenticationManager {

	/**
	 * 
	 * Seta manualmente na sessão a pessoa logada
	 * 
	 * @param authentication
	 */
	public void setAuthentication(Authentication authentication){
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
	}
	
}
