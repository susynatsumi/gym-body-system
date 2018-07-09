package br.com.eits.boot.application.security;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.eits.boot.domain.entity.account.Pessoa;

/**
 *
 */
public abstract class RequestContext
{
	/*-------------------------------------------------------------------
	 * 		 						BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 *
	 * @return
	 */
	public static Optional<Pessoa> currentUser()
	{
		return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication() )
				.map( auth -> (Pessoa) auth.getPrincipal() );
	}
}
