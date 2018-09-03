package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao.anamnese;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.anamnese.Resposta;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.anamnese.IRespostaRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.anamnese.RespostaService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class RespostaServiceIntegrationTests  extends AbstractIntegrationTests {

	// ------------------------------------------------------
	// ------------------- ATRIBUTOS ------------------------
	// ------------------------------------------------------
	
	@Autowired
	private IRespostaRepository respostaRepository;
	
	@Autowired
	private RespostaService respostaService;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma nova resposta
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/anamnese/respostas.sql"
	})
	public void insertRespostaMustPass(){
		
		Resposta resposta = new Resposta(
			null, 
			"objetivosAtividadeFisica", 
			"praticaAtividade", 
			"medicamento", 
			"cirurgia", 
			"doencaFamiliar", 
			"observacao" 
		);
		
		resposta = this.respostaService.insertResposta(resposta);
		
		Assert.assertNotNull(resposta);
		Assert.assertNotNull(resposta.getId());
		
	}
	
	/**
	 * testa obrigatoriedade dos campos da resposta
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/anamnese/respostas.sql"
	})
	public void insertRespostaMustFail(){
		
		Resposta resposta = new Resposta();
		
		resposta = this.respostaService.insertResposta(resposta);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	* Teste de update de uma resposta com sucesso 
	*/
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/anamnese/respostas.sql"
	})
	public void updateRespostaMustPass(){

		Resposta resposta = this.respostaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(resposta);
		
		final String cirurgia = "Cirurgia de teste 2018-09-02";
		
		resposta.setCirurgia(cirurgia);
		
		this.respostaService.updateResposta(resposta);
		
		resposta = this.respostaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(resposta);
		Assert.assertEquals(cirurgia, resposta.getCirurgia());
		
	}
	
	/**
	 * Update de uma resposta com o valor inválido
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/anamnese/respostas.sql"
	})
	public void updateRespostaMustFail(){

		Resposta resposta = this.respostaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(resposta);
		
		resposta.setCirurgia("");
		
		this.respostaService.updateResposta(resposta);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Teste de busca de resposta por um id existente
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/anamnese/respostas.sql"
	})
	public void findRespostaMustPassById(){
		
		Resposta resposta = this.respostaService
				.findRespostaById(1000L);
		
		Assert.assertNotNull(resposta);
		Assert.assertNotNull(resposta.getId());
		
	}
	
	/**
	 * Teste de busca por uma resposta com um id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/anamnese/respostas.sql"
	})
	public void findRespostaMustFailById(){
		
		this.respostaService
			.findRespostaById(1001651L);
		
	}
	

}
