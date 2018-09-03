package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.Perimetria;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.IPerimetriaRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.PerimetriaService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class PerimetriaServiceIntegrationTests extends AbstractIntegrationTests {

	
	@Autowired
	private IPerimetriaRepository perimetriaRepository;
	
	@Autowired
	private PerimetriaService perimetriaService;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova perimetria
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/perimetrias.sql"
	})
	public void insertPerimetriaMustPass(){
		
		Perimetria perimetria = new Perimetria(
			null, 
			10d, 
			50d, 
			5d, 
			5d, 
			8d, 
			8d, 
			6d, 
			6d, 
			100d, 
			100d, 
			100d, 
			80d, 
			80d, 
			70d, 
			70d, 
			60d, 
			60d, 
			50d, 
			50d 
		);
		
		perimetria = this.perimetriaService.insertPerimetria(perimetria);
		
		Assert.assertNotNull(perimetria);
		Assert.assertNotNull(perimetria.getId());
		Assert.assertNotNull(perimetria.getAbdomen());
		
	}
	
	/**
	 * Falha na inserção de uma nova perimetria com dados inválidos
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/perimetrias.sql"
	})
	public void insertPerimetriaMustFail(){

		Perimetria perimetria = new Perimetria();
		
		perimetria = this.perimetriaService.insertPerimetria(perimetria);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	* Teste de update de uma perimetria
	*/
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/perimetrias.sql"
	})
	public void updatePerimetriaMustPass(){
		
		Perimetria perimetria = this.perimetriaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(perimetria);
		
		perimetria.setAbdomen(666d);

		this.perimetriaService.updatePerimetria(perimetria);
		
		perimetria = this.perimetriaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(perimetria);
		Assert.assertTrue(perimetria.getAbdomen().equals(666d));
		
	}
	
	/**
	 * Falha no update de uma parimetria setando dados inválidos 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/perimetrias.sql"
	})
	public void updatePerimetriaMustFail(){
		
		Perimetria perimetria = this.perimetriaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(perimetria);
		
		perimetria.setAbdomen(null);
		perimetria.setBracoDireitoRelaxado(null);
		
		this.perimetriaService.updatePerimetria(perimetria);
		
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
		"/dataset/academia/avaliacaoFisica/perimetrias.sql"
	})
	public void findPerimetriaMustPassById(){

		Perimetria perimetria = this.perimetriaService
				.findPerimetriaById(1000L);
		
		Assert.assertNotNull(perimetria);
		Assert.assertNotNull(perimetria.getId());
		
	}
	
	/**
	 * Teste de busca de uma perimetria por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/perimetrias.sql"
	})
	public void findPerimetriaMustFailById(){
		
		this.perimetriaService
			.findPerimetriaById(151615615L);
		
	}
	
}
