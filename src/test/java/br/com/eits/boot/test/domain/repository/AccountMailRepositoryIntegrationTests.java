package br.com.eits.boot.test.domain.repository;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

import br.com.eits.boot.test.domain.AbstractIntegrationTests;

/**
 * 
 * @author rodrigo@eits.com.br
 * @since 09/05/2013
 * @version 1.0
 */
public class AccountMailRepositoryIntegrationTests extends AbstractIntegrationTests
{
	/*-------------------------------------------------------------------
	 *                           ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
     * 
     */
//	@Autowired
//	private IAccountMailRepository accountMailRepository;

	/*-------------------------------------------------------------------
	 *                           TESTS
	 *-------------------------------------------------------------------*/
	/**
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     * 
     */
	@Test
	public void sendNewUserAccountTestMustPass() throws InterruptedException, ExecutionException
	{
//		final Pessoa user = new Pessoa();
//		user.setEmail( "eits@mailinator.com" );
//		user.setNome( "Suporte da eits" );
//
//		final Future<Void> emailSent = this.accountMailRepository.sendNewUserAccount( user );
//		
//		Assert.assertNotNull( emailSent );
//		Assert.assertNull( emailSent.get() );
	}
}
