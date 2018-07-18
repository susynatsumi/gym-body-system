package br.com.eits.boot.test.domain.service.academia.notificacao;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.Academia;
import br.com.eits.boot.domain.entity.academia.notificacao.Notificacao;
import br.com.eits.boot.domain.entity.academia.notificacao.PessoaNotificacao;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.IAcademiaRepository;
import br.com.eits.boot.domain.repository.academia.notificacao.INotificacaoRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.notificacao.NotificacaoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class NotificacaoServiceIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private INotificacaoRepository notificacaoRepository;
	
	@Autowired
	private NotificacaoService notificacaoService;
	
	@Autowired
	private IAcademiaRepository academiaRepository;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	private List<PessoaNotificacao> mockPessoasNotificacoes(){
		
		Pessoa remetente = this.pessoaRepository
				.findById(1011L)
				.orElse(null);
		
		Pessoa destinatario = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		return Arrays.asList(
				new PessoaNotificacao(remetente, null, false),
				new PessoaNotificacao(destinatario, null, true)
		);
		
	}
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova notificação
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustPass(){
		
		final Academia academia = this.academiaRepository
				.findById(1002L)
				.orElse(null);
		
		Notificacao notificacao = new Notificacao(
			"Titulo",
			"asdfafasjlkfajslçkfa",
			null, 
			academia,
			mockPessoasNotificacoes()
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
		Assert.assertNotNull(notificacao);
		Assert.assertNotNull(notificacao.getId());
		
	}
	
	/**
	 * Valida obrigatoriedade do campo titulo da notificacao
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailMandatoryFieldTitulo(){
		
		final Academia academia = this.academiaRepository
				.findById(1002L)
				.orElse(null);
		
		Notificacao notificacao = new Notificacao(
			"",
			"asdfafasjlkfajslçkfa",
			null, 
			academia,
			mockPessoasNotificacoes()
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	/**
	 * Valida obrigatoriedade do campo descricao da notificacao
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailMandatoryFieldDescricao(){
		
		final Academia academia = this.academiaRepository
				.findById(1002L)
				.orElse(null);
		
		Notificacao notificacao = new Notificacao(
			"asdfasffasdfa",
			"",
			null, 
			academia,
			mockPessoasNotificacoes()
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	/**
	 * Verifica se a valiação de destinatarios da notificação está ok
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailEmptyListDestinatarios(){
		
		final Academia academia = this.academiaRepository
				.findById(1002L)
				.orElse(null);
		
		Pessoa pessoa = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		List<PessoaNotificacao> remetente = Arrays.asList(
				new PessoaNotificacao(pessoa, null, false)
		);
		
		Notificacao notificacao = new Notificacao(
			"asdfasffasdfa",
			"",
			null, 
			academia,
			remetente
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	/**
	 * Verifica se a valiação de remetente está ok
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailEmptyListRemetente(){
		
		final Academia academia = this.academiaRepository
				.findById(1002L)
				.orElse(null);
		
		Pessoa pessoa = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		List<PessoaNotificacao> destinatario = Arrays.asList(
				new PessoaNotificacao(pessoa, null, true)
		);
		
		Notificacao notificacao = new Notificacao(
			"asdfasffasdfa",
			"",
			null, 
			academia,
			destinatario
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	 * Teste de update do texto da notificacao 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void updateNotificacaoMustPassMandatoryFieldDescricao(){

		Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(notificacao);
	
		notificacao.setTexto("Novo testes");
		
		this.notificacaoService.updateNotificacao(notificacao);
		
		notificacao = this.notificacaoRepository
					.findById(1000L)
					.orElse(null);
		
		Assert.assertNotNull(notificacao);
		Assert.assertEquals("Novo testes", notificacao.getTexto());
		
	}
	
	/**
	 * Teste de update do titulo da notificacao 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void updateNotificacaoMustPassMandatoryFieldTitulo(){

		Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(notificacao);
	
		notificacao.setTitulo("Novo testes");
		
		this.notificacaoService.updateNotificacao(notificacao);
		
		notificacao = this.notificacaoRepository
					.findById(1000L)
					.orElse(null);
		
		Assert.assertNotNull(notificacao);
		Assert.assertEquals("Novo testes", notificacao.getTitulo());
		
	}
	
	/**
	 * Teste de update do texto da notificacao invalido
	 */
	@Test( expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void updateNotificacaoMustFailMandatoryFieldDescricao(){
		
		Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(notificacao);
		
		notificacao.setTexto("");
		
		this.notificacaoService.updateNotificacao(notificacao);
		
	}
	
	/**
	 * Teste de update do titulo da notificacao 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void updateNotificacaoMustFailMandatoryFieldTitulo(){
		
		Notificacao notificacao = this.notificacaoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(notificacao);
		
		notificacao.setTitulo("");
		
		this.notificacaoService.updateNotificacao(notificacao);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void findNotificacaoMustPassById(){

		Notificacao notificacao = this.notificacaoService
				.findNotificacaoById(1000L);
				
		Assert.assertNotNull(notificacao);
		Assert.assertNotNull(notificacao.getId());
		
	}
	
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void findNotificacaoMustFailById(){
		
		this.notificacaoService
			.findNotificacaoById(151615615L);
		
	}
	
	
	//TODO fazer testes com filtros
	
}
