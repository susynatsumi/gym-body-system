package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IDobrasCutaneasRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneasService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class DobrasCutaneasServiceIntegrationTests extends AbstractIntegrationTests {

	
	@Autowired
	private IDobrasCutaneasRepository dobrasCutaneasReposotory;
	
	@Autowired
	private DobrasCutaneasService dobrasCutaneasService;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova dobra cutanea
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/dobrasCutaneas.sql"
	})
	public void insertDobrasCutaneasMustPass(){

		DobrasCutaneas dobrasCutaneas = new DobrasCutaneas(
			null, 
			10d, 
			11d, 
			16d, 
			15d, 
			156d, 
			11d, 
			156d, 
			165d, 
			100d, 
			165d
		);
		
		dobrasCutaneas = this.dobrasCutaneasService.insertDobraCutanea(dobrasCutaneas);
		
		Assert.assertNotNull(dobrasCutaneas);
		Assert.assertNotNull(dobrasCutaneas.getId());
		
	}
	
	/**
	 * Falha na inserção de uma nova dobra cutanea com dados inválidos
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/dobrasCutaneas.sql"
	})
	public void insertDobrasCutaneasMustFail(){

		DobrasCutaneas dobrasCutaneas = new DobrasCutaneas();
		
		this.dobrasCutaneasService.insertDobraCutanea(dobrasCutaneas);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	* Teste de update de uma dobra cutanea
	*/
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/dobrasCutaneas.sql"
	})
	public void updateDobraCutaneaMustPass(){

		DobrasCutaneas dobrasCutaneas = this.dobrasCutaneasReposotory
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(dobrasCutaneas);
		
		dobrasCutaneas.setBicital(1616d);
		
		this.dobrasCutaneasService.updateDobraCutanea(dobrasCutaneas);
		
		dobrasCutaneas = this.dobrasCutaneasReposotory
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(dobrasCutaneas);
		Assert.assertEquals(Double.valueOf(1616d), dobrasCutaneas.getBicital());
		
	}
	
	/**
	 * Falha no update de uma dobra cutanea setando dados inválidos 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/dobrasCutaneas.sql"
	})
	public void updateDobraCutaneaMustFail(){

		DobrasCutaneas dobrasCutaneas = this.dobrasCutaneasReposotory
				.findById(1001L)
				.orElse(null);
		
		Assert.assertNotNull(dobrasCutaneas);
		
		dobrasCutaneas.setAbdominal(-1d);
		
		this.dobrasCutaneasService.updateDobraCutanea(dobrasCutaneas);
		
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
		"/dataset/academia/avaliacaoFisica/antopometrica/dobrasCutaneas.sql"
	})
	public void findDobraCutaneaMustPassById(){

		DobrasCutaneas dobrasCutaneas = this.dobrasCutaneasService
				.findDobraCutaneaById(1000L);
		
		Assert.assertNotNull(dobrasCutaneas);
		Assert.assertNotNull(dobrasCutaneas.getId());
		
	}
	
	/**
	 * Teste de busca de uma perimetria por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/dobrasCutaneas.sql"
	})
	public void findDobraCutaneaMustFailById(){
		
		this.dobrasCutaneasService
			.findDobraCutaneaById(151615615L);
		
	}
	
}
