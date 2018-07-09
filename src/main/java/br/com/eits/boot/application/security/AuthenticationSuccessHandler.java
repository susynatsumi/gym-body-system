package br.com.eits.boot.application.security;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;

/**
 * @author rodrigo@eits.com.br
 */
@Component
public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler
{
	/**
	 *
	 */
	private static final Logger LOG = Logger.getLogger( AuthenticationSuccessHandler.class.getName() );
	
	/*-------------------------------------------------------------------
	 * 		 					 ATTRIBUTES
	 *-------------------------------------------------------------------*/
	//Repositories
	/**
	 *
	 */
	private final IPessoaRepository pessoaRepository;

	private final ObjectMapper objectMapper;

	@Autowired
	public AuthenticationSuccessHandler( IPessoaRepository pessoaRepository, ObjectMapper objectMapper )
	{
		this.pessoaRepository = pessoaRepository;
		this.objectMapper = objectMapper;
	}

	/*-------------------------------------------------------------------
	 * 		 					BEHAVIORS
	 *-------------------------------------------------------------------*/

	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException
	{
		try
		{
			final Pessoa user = RequestContext.currentUser().map( Pessoa::getId ).flatMap( this.pessoaRepository::findById ).get();
			user.setLastLogin( OffsetDateTime.now() );
			this.pessoaRepository.save( user );
			this.objectMapper.writeValue( response.getWriter(), user );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			LOG.severe( "Ocorreu um problema ao atualizar o ultimo login do usuário" );
		}
	}
}
