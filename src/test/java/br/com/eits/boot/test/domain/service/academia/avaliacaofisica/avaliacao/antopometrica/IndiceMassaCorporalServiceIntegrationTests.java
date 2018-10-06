package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import java.math.BigDecimal;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IIndiceMassaCorporalRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporalService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class IndiceMassaCorporalServiceIntegrationTests extends AbstractIntegrationTests {

	
	@Autowired
	private IIndiceMassaCorporalRepository indiceMassaCorporalRepository;
	
	@Autowired
	private IndiceMassaCorporalService indiceMassaCorporalService;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma novo indice massa corporal
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/indiceMassaCorporal.sql"
	})
	public void insertIndiceMassaCorporalMustPass(){

		IndiceMassaCorporal indiceMassaCorporal = new IndiceMassaCorporal(
			null, 
			BigDecimal.valueOf(1.90d), 
			BigDecimal.valueOf(100),
			BigDecimal.valueOf(20)
		);
		
		indiceMassaCorporal = this.indiceMassaCorporalService
				.insertIndiceMassaCorporal(indiceMassaCorporal);
		
		Assert.assertNotNull(indiceMassaCorporal);
		Assert.assertNotNull(indiceMassaCorporal.getId());
	}
	
	/**
	 * Falha na inserção de um novo indice de massa corporal
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/indiceMassaCorporal.sql"
	})
	public void insertIndiceMassaCorporalMustFail(){

		IndiceMassaCorporal indiceMassaCorporal = new IndiceMassaCorporal();
		
		this.indiceMassaCorporalService.insertIndiceMassaCorporal(indiceMassaCorporal);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	* Teste de update de um indice de massa corporal
	*/
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/indiceMassaCorporal.sql"
	})
	public void updateIndiceMassaCorporalMustPass(){
		
		IndiceMassaCorporal indiceMassaCorporal = this.indiceMassaCorporalRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(indiceMassaCorporal);
		
		BigDecimal bigDecimal = new BigDecimal(50);
		
		indiceMassaCorporal.setPeso(bigDecimal);
		
		this.indiceMassaCorporalService.updateIndiceMassaCorporal(indiceMassaCorporal);
		
		indiceMassaCorporal = this.indiceMassaCorporalRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(indiceMassaCorporal);
		Assert.assertEquals(
			bigDecimal.longValue(), 
			indiceMassaCorporal.getPeso().longValue()
		);
		
	}
	
	/**
	 * Falha no update de um idice de massa corporal 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/indiceMassaCorporal.sql"
	})
	public void updateIndiceMassaCorporalMustFail(){

		IndiceMassaCorporal indiceMassaCorporal = this.indiceMassaCorporalRepository
				.findById(1000L)
				.orElse(null);
		
		indiceMassaCorporal.setPeso(BigDecimal.valueOf(-1));
		
		this.indiceMassaCorporalService.updateIndiceMassaCorporal(indiceMassaCorporal);
		
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
		"/dataset/academia/avaliacaoFisica/antopometrica/indiceMassaCorporal.sql"
	})
	public void findIndiceMassaCorporalMustPassById(){

		IndiceMassaCorporal indiceMassaCorporal = this.indiceMassaCorporalService
				.findIndiceMassaCorporalById(1001L);
		
		Assert.assertNotNull(indiceMassaCorporal);
		Assert.assertNotNull(indiceMassaCorporal.getId());
		
	}
	
	/**
	 * Teste de busca de uma perimetria por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/antopometrica/indiceMassaCorporal.sql"
	})
	public void findIndiceMassaCorporalMustFailById(){
		
		this.indiceMassaCorporalService
			.findIndiceMassaCorporalById(151615615L);
		
	}
	
}
