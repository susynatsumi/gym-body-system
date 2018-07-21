package br.com.eits.boot.test.domain.entity.account;

import org.junit.Assert;
import org.junit.Test;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.test.domain.AbstractUnitTests;

/**
 * 
 * @author rodrigo@eits.com.br
 * @since 09/05/2013
 * @version 1.0
 */
public class PessoaTests extends AbstractUnitTests
{
	/*-------------------------------------------------------------------
	 *                           ATTRIBUTES
	 *-------------------------------------------------------------------*/

	/*-------------------------------------------------------------------
	 *                           TESTS
	 *-------------------------------------------------------------------*/
	/**
     * 
     */
	@Test
	public void getAuthoritiesMustPass()
	{
		final Pessoa user = new Pessoa();
		user.setPapel( Papel.ADMINISTRATOR );
		
		Assert.assertNotNull( user.getAuthorities() );
		Assert.assertTrue( user.getAuthorities().contains( Papel.ADMINISTRATOR ) );
		Assert.assertTrue( user.getAuthorities().contains( Papel.PERSONAL ) );
		Assert.assertTrue( user.getAuthorities().contains( Papel.ALUNO ) );
	}
}