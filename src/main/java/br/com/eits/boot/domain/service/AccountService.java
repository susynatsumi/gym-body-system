package br.com.eits.boot.domain.service;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.eits.boot.application.configuration.settings.AppSettings;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.IAccountMailRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;

/**
 * @author rodrigo@eits.com.br
 */
@Service
@RemoteProxy
@Transactional
public class AccountService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Password encoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	//Repositories
	/**
	 *
	 */
	@Autowired
	private IPessoaRepository userRepository;

	@Autowired
	private IAccountMailRepository accountMailRepository;

	@Autowired
	private AppSettings appSettings;

	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/

	/**
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Pessoa insertUser( Pessoa user )
	{
		user.setIsAtivo( true );
		user.setSenha( this.passwordEncoder.encode( user.getPassword() ) );

		user = this.userRepository.save( user );
		this.accountMailRepository.sendNewUserAccount( user );
		return user;
	}

	/**
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Pessoa findUserById( long id )
	{
		return this.userRepository.findById( id )
				.orElseThrow( () -> new IllegalArgumentException( translate( "repository.notFoundById", id ) ) );
	}

	/**
	 * @param pageable
	 * @param filter
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Pessoa> listUsersByFilters( String filter, PageRequest pageable )
	{
		return this.userRepository.listByFilters( filter, pageable );
	}

	/**
	 *
	 */
	public void sendPasswordResetToken( String email )
	{
		Pessoa user = this.userRepository.findByEmail( email )
				.orElseThrow( () -> new IllegalArgumentException( translate( "userService.userNotFoundByEmail", email ) ) );
		user.setPasswordResetToken( UUID.randomUUID().toString() );
		user.setPasswordResetTokenExpiration( OffsetDateTime.now().plusHours( appSettings.getPasswordTokenExpirationHours() ) );
		user = this.userRepository.save( user );
		this.accountMailRepository.sendPasswordReset( user );
	}

	/**
	 *
	 */
	public void setUserPasswordByToken( String token, String newPassword )
	{
		Pessoa user = this.userRepository.findByPasswordResetTokenAndPasswordResetTokenExpirationAfter( token, OffsetDateTime.now() )
				.orElseThrow( () -> new IllegalArgumentException( translate( "userService.passwordResetTokenInvalid" ) ) );
		user.setPasswordResetToken( null );
		user.setPasswordResetTokenExpiration( null );
		user.setSenha( this.passwordEncoder.encode( newPassword ) );
		user = this.userRepository.save( user );
		this.accountMailRepository.sendPasswordResetNotice( user );
	}
}