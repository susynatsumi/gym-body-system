package br.com.eits.boot.test.domain.service;

import java.time.LocalDate;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
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
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/*-------------------------------------------------------------------
	 *				 		      TESTS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	public void insertUserMustFail() 
	{
		this.accountService.insertPessoa( new Pessoa() );
	}
	
	/**
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void insertUserMustPass()
	{
		Pessoa user = new Pessoa( 
			null, 
			"Testing user", 
			"teste.emial@email.com", 
			true, 
			Papel.ALUNO, 
			"user", 
			LocalDate.of(1995, 5, 1),
			Genero.MASCULINO
		);
		
		user = this.accountService.insertPessoa( user );

		Assert.assertNotNull( user );
		Assert.assertNotNull( user.getId() );
		Assert.assertNotNull( user.getCreated() );
		Assert.assertTrue( user.getIsAtivo() );
		Assert.assertFalse( user.getPassword().equals( "user" ) );
	}
	
	//-------------------------------------------------
	//----------- INSERTS FAILS -----------------------
	//-------------------------------------------------
	
	/**
	 * Testa inserção de pessoa com valor inválido para nome
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void insertUserMustFailMandatoryFieldNome(){
		Pessoa user = new Pessoa( 
			null,
			null, 
			"teste.emial@email.com", 
			true, 
			Papel.ALUNO, 
			"aluno" ,
			LocalDate.of(1995, 5, 1),
			Genero.FEMININO
		);
		
		user = this.accountService.insertPessoa( user );

	}
	
	/**
	 * Testa inserção de pessoa com valor inválido para e-mail
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void insertUserMustFailMandatoryFieldEmail(){
		Pessoa user = new Pessoa( 
			null, 
			"Teste", 
			null, 
			true, 
			Papel.ALUNO, 
			"aluno",
			LocalDate.of(1995, 5, 1),
			Genero.FEMININO
		);
		user = this.accountService.insertPessoa( user );

	}
	
	/**
	 * Testa inserção de pessoa com valor inválido para papel
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void insertUserMustFailMandatoryFieldPapel(){
		Pessoa user = new Pessoa( 
			null, 
			"Teste", 
			"testezinho@email.com", 
			true, 
			null, 
			"aluno",
			LocalDate.of(1995, 5, 1),
			Genero.FEMININO
		);
		user = this.accountService.insertPessoa( user );

	}
	
	//-------------------------------------------------
	//----------- UPDATES PASS-------------------------
	//-------------------------------------------------
	
	/**
	 * Testa update de pessoa, alterando valor do campo nome
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetUserMustPassMandatoryFieldNome(){
		
		final String novoNome  = "João Teste da Silva Junior";
		
		Pessoa pessoa = this.pessoaRepository.findById(1011L).orElse(null);
		pessoa.setNome(novoNome);
		
		pessoa = accountService.updatePessoa(pessoa);
		
		Assert.assertEquals(novoNome, pessoa.getNome());
		
	}
	
	/**
	 * Testa update de pessoa, alterando valor do campo e-mail
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetUserMustPassMandatoryFieldEmail(){
		
		final String novoEmail  = "joao.teste@email.com";
		
		Pessoa pessoa = this.pessoaRepository.findById(1011L).orElse(null);
		pessoa.setEmail(novoEmail);
		
		accountService.updatePessoa(pessoa);

		Assert.assertEquals(novoEmail, pessoa.getEmail());
	}

	
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetUserMustPassSenha(){
		
		final Long id = 1011L;
		
		accountService.alterarSenha(id, "sdajfjlçadfjsakjfksafoaspjioapjriojaiorjqiojrioqjw");
		
		Pessoa pessoa = this.pessoaRepository.findById(id).orElse(null);

		Assert.assertTrue(
			this.passwordEncoder
				.matches(
					"sdajfjlçadfjsakjfksafoaspjioapjriojaiorjqiojrioqjw", 
					pessoa.getSenha()
				)
		);
	}
	
	//-------------------------------------------------
	//----------- UPDATE FAIL -----------------------------
	//-------------------------------------------------
	
	/**
	 * Testa update de pessoa, alterando valor do campo senha pelo método incorreto
	 */
	@Test(expected = AssertionError.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetUserFailSenha(){
		
		Pessoa pessoa = this.pessoaRepository.findById(1011L).orElse(null);
		
		final String senhaAntiga = pessoa.getSenha();
		
		pessoa.setSenha("sdafateste");
		
		accountService.updatePessoa(pessoa);

		Assert.assertNotNull(pessoa);
		Assert.assertEquals(senhaAntiga, pessoa.getSenha());
	}
	
	/**
	 * Teste deve falhar, pois o campo nome não pode ser vazio
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetPessoaFailMandatoryFieldNome(){
		
		Pessoa pessoa = this.pessoaRepository.findById(1011L).orElse(null);
		pessoa.setNome("");
		
		accountService.updatePessoa(pessoa);

	}
	
	/**
	 * Teste deve falhar, pois o campo e-mail não pode ser vazio
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetPessoaFailMandatoryFieldEmail(){
		
		Pessoa pessoa = this.pessoaRepository.findById(1011L).orElse(null);
		pessoa.setEmail("");
		
		accountService.updatePessoa(pessoa);

	}
	
	/**
	 * Teste deve falhar, pois o campo papel não pode ser vazio
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void updatetPessoaFailMandatoryFieldPapel(){
		
		Pessoa pessoa = this.pessoaRepository.findById(1011L).orElse(null);
		pessoa.setPapel(null);
		
		accountService.updatePessoa(pessoa);

	}
	
	//-------------------------------------------------
	//----------- FINDS -----------------------------
	//-------------------------------------------------
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/pessoa/pessoas.sql"
	})
	public void findUserByIdMustPass()
	{
		final Pessoa user = this.accountService.findPessoaById( 1011L );
	
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
		"/dataset/pessoa/pessoas.sql"
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
		"/dataset/pessoa/pessoas.sql"
	})
	public void listUsersByFiltersMustReturn3()
	{
		final Page<Pessoa> users = this.accountService.listUsersByFilters( "1011,1012,xó", null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 3, users.getTotalElements());
	}
	
	/**
	 * 
	 */
	@Test
	@Sql({
		"/dataset/pessoa/pessoas.sql"
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
		"/dataset/pessoa/pessoas.sql"
	})
	public void listUsersByFiltersMustReturnAll()
	{
		final Page<Pessoa> users = this.accountService.listUsersByFilters( null, null );
		
		Assert.assertNotNull( users );
		Assert.assertEquals( 4, users.getTotalElements() );
	}
}