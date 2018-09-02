package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.notificacao.DestinatarioNotificacao;
import br.com.eits.boot.domain.entity.academia.notificacao.Notificacao;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.IPerimetriaRepository;
import br.com.eits.boot.domain.repository.academia.notificacao.INotificacaoRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.PerimetriaService;
import br.com.eits.boot.domain.service.academia.notificacao.NotificacaoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class PerimetriaServiceIntegrationTestes extends AbstractIntegrationTests {


	
	@Autowired
	private IPerimetriaRepository perimetriaRepository;
	
	@Autowired
	private PerimetriaService perimetriaService;
	
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	/*
	*//**
	 * Insere uma nova perimetria
	 *//*
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertPerimetriaMustPass(){
		
		Notificacao notificacao = new Notificacao(
			"Titulo",
			"asdfafasjlkfajslçkfa",
			mockDestinatarios()
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
		Assert.assertNotNull(notificacao);
		Assert.assertNotNull(notificacao.getId());
		
	}
	
	*//**
	 * Valida obrigatoriedade do campo titulo da notificacao
	 *//*
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailMandatoryFieldTitulo(){
		
		Notificacao notificacao = new Notificacao(
			"",
			"asdfafasjlkfajslçkfa",
			mockDestinatarios()
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	*//**
	 * Valida obrigatoriedade do campo descricao da notificacao
	 *//*
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailMandatoryFieldDescricao(){
		
		Notificacao notificacao = new Notificacao(
			"asdfasffasdfa",
			"",
			mockDestinatarios()
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	*//**
	 * Verifica se a valiação de destinatarios da notificação está ok
	 *//*
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/notificacao/notificacoes.sql"
	})
	public void insertNotificacaoMustFailEmptyListDestinatarios(){
		
		Notificacao notificacao = new Notificacao(
			"asdfasffasdfa",
			"",
			null
		);
		
		notificacao = this.notificacaoService
			.insertNotificacao(notificacao);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	*//**
	 * Teste de update do texto da notificacao 
	 *//*
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
	
	*//**
	 * Teste de update do titulo da notificacao 
	 *//*
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
	
	*//**
	 * Teste de update do texto da notificacao invalido
	 *//*
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
	
	*//**
	 * Teste de update do titulo da notificacao 
	 *//*
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
*/	
}
