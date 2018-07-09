package br.com.eits.boot.test.domain.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.service.AccountService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

/**
 * 
 * @author rodrigo@eits.com.br
 */
public class AccountServiceIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Autowired
	private AccountService accountService;
	
	/*-------------------------------------------------------------------
	 *				 		      TESTS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void insertUserMustFail() 
	{
		this.accountService.insertUser( new Pessoa() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/account/users.sql"
	})
	public void insertUserMustPass()
	{
		Pessoa user = new Pessoa( null, "Testing user", "teste.emial@email.com", true, Papel.ALUNO, "user" );
		user = this.accountService.insertUser( user );

		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertTrue( user.getIsAtivo() );
		Assert.assertFalse( user.getPassword().equals( "user" ) );
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/account/users.sql"
	})
	public void findUserByIdMustPass()
	{
		final Pessoa user = this.accountService.findUserById( 9999L );
	
		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertEquals( "admin@email.com", user.getEmail() );
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/account/users.sql"
	})
	public void listUsersByFiltersMustReturn2()
	{
		final Page<Pessoa> users = this.accountService.listUsersByFilters( "user", null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 2, users.getTotalElements() );
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/account/users.sql"
	})
	public void listUsersByFiltersMustReturn3()
	{
		final Page<Pessoa> users = this.accountService.listUsersByFilters( "1000,1001,xó", null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 3, users.getTotalElements());
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/account/users.sql"
	})
	public void listUsersByFiltersMustReturn1()
	{
		final Page<Pessoa> users = this.accountService.listUsersByFilters( "xó", null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 1, users.getTotalElements());
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/account/users.sql"
	})
	public void listUsersByFiltersMustReturnAll()
	{
		final Page<Pessoa> users = this.accountService.listUsersByFilters( null, null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 4, users.getTotalElements() );
	}
}