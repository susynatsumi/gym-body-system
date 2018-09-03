package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.protocolos;

import java.math.BigDecimal;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.ProtocoloGuedes;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.protocolos.IProtocoloGuedesRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.protocolos.ProtocoloGuedesService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class ProtocoloGuedesServiceIntegrationTests extends AbstractIntegrationTests {

	
	@Autowired
	private IProtocoloGuedesRepository protocoloGuedesRepository;
	
	@Autowired
	private ProtocoloGuedesService protocoloGuedesService;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Faz mock de dobras cutaneas 
	 * @return
	 */
	private DobrasCutaneas mockDobrasCutaneas(){
	
		return new DobrasCutaneas(
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
	}
		
	/**
	 * Faz mock de indice de massa corporal
	 * @return
	 */
	private IndiceMassaCorporal mockIndiceMassaCorporal(){
		return new IndiceMassaCorporal(
			null, 
			BigDecimal.valueOf(1.90d), 
			BigDecimal.valueOf(100)
		);
	}
	
	/**
	 * Faz mock de predicao gorgura siri
	 * @return
	 */
	private PredicaoGorduraSiri mockPredicaoGorduraSiri(){
		return new PredicaoGorduraSiri(
			null, 
			10d, 
			16d
		);
	}
	
	/**
	 * Insere uma nova ProtocoloGuedes
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloGuedes.sql"
	})
	public void insertGuedesMustPass(){
		
		ProtocoloGuedes protocoloGuedes = new ProtocoloGuedes(
			null, 
			mockDobrasCutaneas(), 
			mockIndiceMassaCorporal(), 
			mockPredicaoGorduraSiri()
		);
		
		protocoloGuedes = this.protocoloGuedesService.insertProtocoloGuedes(protocoloGuedes);
		
		Assert.assertNotNull(protocoloGuedes);
		Assert.assertNotNull(protocoloGuedes.getId());
		
	}
	
	/**
	 * Falha na inserção de uma nova ProtocoloGuedes com dados inválidos
	 */
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloGuedes.sql"
	})
	public void insertProtocoloGuedesMustFail(){

		ProtocoloGuedes ProtocoloGuedes = new ProtocoloGuedes();
		
		ProtocoloGuedes = this.protocoloGuedesService.insertProtocoloGuedes(ProtocoloGuedes);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

//	/**
//	* Teste de update de uma ProtocoloGuedes
//	*/
//	@Test
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/avaliacaoFisica/protocolos/protocoloGuedes.sql"
//	})
//	public void updateProtocoloGuedesMustPass(){
//		
//		ProtocoloGuedes ProtocoloGuedes = this.ProtocoloGuedesRepository
//				.findById(1000L)
//				.orElse(null);
//		
//		Assert.assertNotNull(ProtocoloGuedes);
//		
//		ProtocoloGuedes.setAbdomen(666d);
//
//		this.ProtocoloGuedesService.updateProtocoloGuedes(ProtocoloGuedes);
//		
//		ProtocoloGuedes = this.ProtocoloGuedesRepository
//				.findById(1000L)
//				.orElse(null);
//		
//		Assert.assertNotNull(ProtocoloGuedes);
//		Assert.assertTrue(ProtocoloGuedes.getAbdomen().equals(666d));
//		
//	}
//	
//	/**
//	 * Falha no update de uma parimetria setando dados inválidos 
//	 */
//	@Test( expected = TransactionSystemException.class )
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/avaliacaoFisica/protocolos/protocoloGuedes.sql"
//	})
//	public void updateProtocoloGuedesMustFail(){
//		
//		ProtocoloGuedes ProtocoloGuedes = this.ProtocoloGuedesRepository
//				.findById(1000L)
//				.orElse(null);
//		
//		Assert.assertNotNull(ProtocoloGuedes);
//		
//		ProtocoloGuedes.setAbdomen(null);
//		ProtocoloGuedes.setBracoDireitoRelaxado(null);
//		
//		this.protocoloGuedesService.updateProtocoloGuedes(ProtocoloGuedes);
//		
//	}
	
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
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloGuedes.sql"
	})
	public void findProtocoloGuedesMustPassById(){

		ProtocoloGuedes ProtocoloGuedes = this.protocoloGuedesService
				.findProtocoloGuedesById(1000L);
		
		Assert.assertNotNull(ProtocoloGuedes);
		Assert.assertNotNull(ProtocoloGuedes.getId());
		
	}
	
	/**
	 * Teste de busca de uma ProtocoloGuedes por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloGuedes.sql"
	})
	public void findProtocoloGuedesMustFailById(){
		
		this.protocoloGuedesService
			.findProtocoloGuedesById(151615615L);
		
	}
	
}
