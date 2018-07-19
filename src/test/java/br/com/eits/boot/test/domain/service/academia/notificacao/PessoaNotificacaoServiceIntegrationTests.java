package br.com.eits.boot.test.domain.service.academia.notificacao;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.notificacao.Notificacao;
import br.com.eits.boot.domain.entity.academia.notificacao.PessoaNotificacao;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.notificacao.INotificacaoRepository;
import br.com.eits.boot.domain.repository.academia.notificacao.IPessoaNotificacaoRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.notificacao.PessoaNotificacaoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class PessoaNotificacaoServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private IPessoaNotificacaoRepository pessoaNotificacaoRepository;
	
	@Autowired
	private PessoaNotificacaoService pessoaNotificacaoService;
	
	@Autowired
	private INotificacaoRepository notificacaoRepository;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova pessoa notificacao notificação
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void insertPessoaNotificacaoMustPass(){

		final Pessoa pessoa = this.pessoaRepository
				.findById(1014L)
				.orElse(null);
		
		final Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		PessoaNotificacao pessoaNotificacao = new PessoaNotificacao(
			pessoa, 
			notificacao,
			false
		);
		
		pessoaNotificacao = this.pessoaNotificacaoService
				.insertPessoaNotificacao(pessoaNotificacao);
		
		Assert.assertNotNull(pessoaNotificacao);
		Assert.assertNotNull(pessoaNotificacao.getId());
		Assert.assertFalse(pessoaNotificacao.getIsDestinatario());
		
	}
	
	/**
	 * Valida obrigatoriedade do vinculo com pessoa
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void insertPessoaNotificacaoMustFailForeignKeyPessoa(){
		
		final Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		PessoaNotificacao pessoaNotificacao = new PessoaNotificacao(
			null, // pessoa 
			notificacao,
			false
		);
		
		pessoaNotificacao = this.pessoaNotificacaoService
				.insertPessoaNotificacao(pessoaNotificacao);
	}
	
	/**
	 * Valida obrigatoriedade do vinculo com notificacao
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void insertPessoaNotificacaoMustFailForeignKeyNotificacao(){
		
		final Pessoa pessoa = this.pessoaRepository
				.findById(1014L)
				.orElse(null);
		
		PessoaNotificacao pessoaNotificacao = new PessoaNotificacao(
			pessoa, 
			null, // notificacao
			false
		);
		
		pessoaNotificacao = this.pessoaNotificacaoService
				.insertPessoaNotificacao(pessoaNotificacao);		
	}
	
	/**
	 * Verifica a unique key entre pessoa e notificacao
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void insertPessoaNotificacaoMustFailUniquePessoaNotificacao(){

		final Pessoa pessoa = this.pessoaRepository
				.findById(1011L)
				.orElse(null);
		
		final Notificacao notificacao = this.notificacaoRepository
				.findById(1002L)
				.orElse(null);
		
		PessoaNotificacao pessoaNotificacao = new PessoaNotificacao(
			pessoa, 
			notificacao,
			true
		);
		
		pessoaNotificacao = this.pessoaNotificacaoService
				.insertPessoaNotificacao(pessoaNotificacao);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	 * Teste de update de pesssoa notificacao com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void updatePessoaNotificacaoMustPass(){

		Pessoa pessoa = this.pessoaRepository
				.findById(1014L)
				.orElse(null);
		
		PessoaNotificacao pessoaNotificacao = this.pessoaNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaNotificacao);
		
		pessoaNotificacao.setPessoa(pessoa);
		
		this.pessoaNotificacaoService.updatePessoaNotificacao(pessoaNotificacao);
		
		pessoaNotificacao = this.pessoaNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaNotificacao);
		Assert.assertNotNull(pessoaNotificacao.getId());
		Assert.assertEquals(pessoa.getId(), pessoaNotificacao.getPessoa().getId());
		
	}
	
	/**
	 * Teste de update com uma pessoa invalida 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void updatePessoaNotificacaoMustFailForeignKeyPessoa(){

		PessoaNotificacao pessoaNotificacao = this.pessoaNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		pessoaNotificacao.setPessoa(null);
		
		this.pessoaNotificacaoService.updatePessoaNotificacao(pessoaNotificacao);
		
	}
	
	/**
	 * Teste de update com uma notificacao Inválida
	 */
	@Test( expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void updatePessoaNotificacaoMustFailForeignKeyNotificacao(){
		
		PessoaNotificacao pessoaNotificacao = this.pessoaNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		pessoaNotificacao.setNotificacao(null);
		
		this.pessoaNotificacaoService.updatePessoaNotificacao(pessoaNotificacao);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Busca de pessoa notificacao por id, que deve ser realizada com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void findPessoaNotificacaoMustPassById(){

		final PessoaNotificacao pessoaNotificacao = this.pessoaNotificacaoService
				.findPessoaNotificacaoById((1000L));

		Assert.assertNotNull(pessoaNotificacao);
		Assert.assertNotNull(pessoaNotificacao.getId());
		
	}
	
	/**
	 * Busca por um id inexistente, deve resultar em exception
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/pessoasNotificacoes.sql"
	})
	public void findPessoaNotificacaoMustFailById(){
		
		this.pessoaNotificacaoService
				.findPessoaNotificacaoById((1006165160L));

		
	}
	
	
	//TODO fazer testes com filtros
	
}
