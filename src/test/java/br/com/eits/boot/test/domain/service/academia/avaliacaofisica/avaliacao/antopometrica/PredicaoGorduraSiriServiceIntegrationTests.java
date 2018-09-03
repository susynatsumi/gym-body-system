package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IPredicaoGorduraSiriRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiriService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class PredicaoGorduraSiriServiceIntegrationTests extends AbstractIntegrationTests {

	
	@Autowired
	private IPredicaoGorduraSiriRepository predicaoGorduraSiriRepository;
	
	@Autowired
	private PredicaoGorduraSiriService predicaoGorduraSiriService;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova predicao gordura siri
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/predicaoGorduraSiri.sql"
	})
	public void insertPredicaoGorduraSiriMustPass(){

		PredicaoGorduraSiri predicaoGorduraSiri = new PredicaoGorduraSiri(
			null, 
			10d, 
			16d
		);
		
		predicaoGorduraSiri = this.predicaoGorduraSiriService
				.insertPredicaoGorduraSiri(predicaoGorduraSiri);
		
		Assert.assertNotNull(predicaoGorduraSiri);
		Assert.assertNotNull(predicaoGorduraSiri.getId());
	}
	
	/**
	 * Falha na inserção de um novo registro com dados invalidos
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/predicaoGorduraSiri.sql"
	})
	public void insertPredicaoGorduraSiriMustFail(){

		PredicaoGorduraSiri predicaoGorduraSiri = new PredicaoGorduraSiri();
		
		this.predicaoGorduraSiriService.insertPredicaoGorduraSiri(predicaoGorduraSiri);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	* Teste de update de um retistro
	*/
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/predicaoGorduraSiri.sql"
	})
	public void updatePredicaoGorduraSiriMustPass(){
		
		PredicaoGorduraSiri predicaoGorduraSiri = this.predicaoGorduraSiriRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(predicaoGorduraSiri);
		
		predicaoGorduraSiri.setDensidadeCorporal(509d);
		
		this.predicaoGorduraSiriService.updatePredicaoGorduraSiri(predicaoGorduraSiri);
		
		predicaoGorduraSiri = this.predicaoGorduraSiriRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(predicaoGorduraSiri);
		Assert.assertEquals(
			Double.valueOf(509d), 
			predicaoGorduraSiri.getDensidadeCorporal()
		);
		
	}
	
	/**
	 * Falha no update de um registro 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/predicaoGorduraSiri.sql"
	})
	public void updatePredicaoGorduraSiriMustFail(){

		PredicaoGorduraSiri PredicaoGorduraSiri = this.predicaoGorduraSiriRepository
				.findById(1000L)
				.orElse(null);
		
		PredicaoGorduraSiri.setGordura(null);
		
		this.predicaoGorduraSiriService.updatePredicaoGorduraSiri(PredicaoGorduraSiri);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Teste de busca por id com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/predicaoGorduraSiri.sql"
	})
	public void findPredicaoGorduraSiriMustPassById(){

		PredicaoGorduraSiri PredicaoGorduraSiri = this.predicaoGorduraSiriService
				.findPredicaoGorduraSiriById(1001L);
		
		Assert.assertNotNull(PredicaoGorduraSiri);
		Assert.assertNotNull(PredicaoGorduraSiri.getId());
		
	}
	
	/**
	 * Teste de busca de um registro por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/predicaoGorduraSiri.sql"
	})
	public void findPredicaoGorduraSiriMustFailById(){
		
		this.predicaoGorduraSiriService
			.findPredicaoGorduraSiriById(151615615L);
		
	}
	
}
