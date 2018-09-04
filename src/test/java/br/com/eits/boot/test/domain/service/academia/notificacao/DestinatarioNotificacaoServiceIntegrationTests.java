package br.com.eits.boot.test.domain.service.academia.notificacao;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.notificacao.DestinatarioNotificacao;
import br.com.eits.boot.domain.entity.academia.notificacao.Notificacao;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.notificacao.IDestinatarioNotificacaoRepository;
import br.com.eits.boot.domain.repository.academia.notificacao.INotificacaoRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.notificacao.DestinatarioNotificacaoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class DestinatarioNotificacaoServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private IDestinatarioNotificacaoRepository destinatarioNotificacaoRepository;
	
	@Autowired
	private DestinatarioNotificacaoService destinatarioNotificacaoService;
	
	@Autowired
	private INotificacaoRepository notificacaoRepository;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova destinatario notificacao
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void insertDestinatarioNotificacaoMustPass(){

		final Pessoa pessoa = this.pessoaRepository
				.findById(1014L)
				.orElse(null);
		
		final Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		DestinatarioNotificacao DestinatarioNotificacao = new DestinatarioNotificacao(
			pessoa, 
			notificacao
		);
		
		DestinatarioNotificacao = this.destinatarioNotificacaoService
				.insertDestinatarioNotificacao(DestinatarioNotificacao);
		
		Assert.assertNotNull(DestinatarioNotificacao);
		Assert.assertNotNull(DestinatarioNotificacao.getId());
		
	}
	
	/**
	 * Valida obrigatoriedade do vinculo com pessoa
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void insertDestinatarioNotificacaoMustFailForeignKeyPessoa(){
		
		final Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		DestinatarioNotificacao DestinatarioNotificacao = new DestinatarioNotificacao(
			null, // pessoa 
			notificacao
		);
		
		DestinatarioNotificacao = this.destinatarioNotificacaoService
				.insertDestinatarioNotificacao(DestinatarioNotificacao);
	}
	
	/**
	 * Valida obrigatoriedade do vinculo com notificacao
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void insertDestinatarioNotificacaoMustFailForeignKeyNotificacao(){
		
		final Pessoa pessoa = this.pessoaRepository
				.findById(1014L)
				.orElse(null);
		
		DestinatarioNotificacao DestinatarioNotificacao = new DestinatarioNotificacao(
			pessoa, 
			null // notificacao
		);
		
		DestinatarioNotificacao = this.destinatarioNotificacaoService
				.insertDestinatarioNotificacao(DestinatarioNotificacao);		
	}
	
	/**
	 * Verifica a unique key entre pessoa e notificacao
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void insertDestinatarioNotificacaoMustFailUniqueDestinatarioNotificacao(){

		final Pessoa pessoa = this.pessoaRepository
				.findById(1011L)
				.orElse(null);
		
		final Notificacao notificacao = this.notificacaoRepository
				.findById(1002L)
				.orElse(null);
		
		DestinatarioNotificacao DestinatarioNotificacao = new DestinatarioNotificacao(
			pessoa, 
			notificacao
		);
		
		DestinatarioNotificacao = this.destinatarioNotificacaoService
				.insertDestinatarioNotificacao(DestinatarioNotificacao);
		
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
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void updateDestinatarioNotificacaoMustPass(){

		Pessoa pessoa = this.pessoaRepository
				.findById(1014L)
				.orElse(null);
		
		DestinatarioNotificacao DestinatarioNotificacao = this.destinatarioNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(DestinatarioNotificacao);
		
		DestinatarioNotificacao.setPessoa(pessoa);
		
		this.destinatarioNotificacaoService.updateDestinatarioNotificacao(DestinatarioNotificacao);
		
		DestinatarioNotificacao = this.destinatarioNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(DestinatarioNotificacao);
		Assert.assertNotNull(DestinatarioNotificacao.getId());
		Assert.assertEquals(pessoa.getId(), DestinatarioNotificacao.getPessoa().getId());
		
	}
	
	/**
	 * Teste de update com uma pessoa invalida 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void updateDestinatarioNotificacaoMustFailForeignKeyPessoa(){

		DestinatarioNotificacao DestinatarioNotificacao = this.destinatarioNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		DestinatarioNotificacao.setPessoa(null);
		
		this.destinatarioNotificacaoService.updateDestinatarioNotificacao(DestinatarioNotificacao);
		
	}
	
	/**
	 * Teste de update com uma notificacao Inválida
	 */
	@Test( expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void updateDestinatarioNotificacaoMustFailForeignKeyNotificacao(){
		
		DestinatarioNotificacao DestinatarioNotificacao = this.destinatarioNotificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		DestinatarioNotificacao.setNotificacao(null);
		
		this.destinatarioNotificacaoService.updateDestinatarioNotificacao(DestinatarioNotificacao);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Busca de destinatario notificacao por id, que deve ser realizada com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void findDestinatarioNotificacaoMustPassById(){

		final DestinatarioNotificacao DestinatarioNotificacao = this.destinatarioNotificacaoService
				.findDestinatarioNotificacaoById((1000L));

		Assert.assertNotNull(DestinatarioNotificacao);
		Assert.assertNotNull(DestinatarioNotificacao.getId());
		
	}
	
	/**
	 * Busca por um id inexistente, deve resultar em exception
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/destinatariosNotificacoes.sql"
	})
	public void findDestinatarioNotificacaoMustFailById(){
		
		this.destinatarioNotificacaoService
				.findDestinatarioNotificacaoById((1006165160L));

		
	}
	
	
}
