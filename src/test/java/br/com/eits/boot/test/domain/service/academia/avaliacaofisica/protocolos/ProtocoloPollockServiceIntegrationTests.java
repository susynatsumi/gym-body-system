package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.protocolos;

import java.math.BigDecimal;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.ProtocoloPollock;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.protocolos.ProtocoloPollockService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class ProtocoloPollockServiceIntegrationTests extends AbstractIntegrationTests {

	
//	@Autowired
//	private IProtocoloPollockRepository protocoloPollockRepository;
	
	@Autowired
	private ProtocoloPollockService protocoloPollockService;
	
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
			BigDecimal.valueOf(100),
			BigDecimal.valueOf(20)
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
	 * Insere uma nova ProtocoloPollock
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloPollock.sql"
	})
	public void insertPollockMustPass(){
		
		ProtocoloPollock protocoloPollock = new ProtocoloPollock(
			null, 
			mockDobrasCutaneas(), 
			mockIndiceMassaCorporal(), 
			mockPredicaoGorduraSiri(),
			10d
		);
		
		protocoloPollock = this.protocoloPollockService.insertProtocoloPollock(protocoloPollock);
		
		Assert.assertNotNull(protocoloPollock);
		Assert.assertNotNull(protocoloPollock.getId());
		
	}
	
	/**
	 * Falha na inserção de uma nova ProtocoloPollock com dados inválidos
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloPollock.sql"
	})
	public void insertProtocoloPollockMustFail(){

		ProtocoloPollock ProtocoloPollock = new ProtocoloPollock();
		
		ProtocoloPollock = this.protocoloPollockService.insertProtocoloPollock(ProtocoloPollock);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

//	/**
//	* Teste de update de uma ProtocoloPollock
//	*/
//	@Test
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/avaliacaoFisica/protocolos/protocoloPollock.sql"
//	})
//	public void updateProtocoloPollockMustPass(){
//		
//		ProtocoloPollock ProtocoloPollock = this.ProtocoloPollockRepository
//				.findById(1000L)
//				.orElse(null);
//		
//		Assert.assertNotNull(ProtocoloPollock);
//		
//		ProtocoloPollock.setAbdomen(666d);
//
//		this.ProtocoloPollockService.updateProtocoloPollock(ProtocoloPollock);
//		
//		ProtocoloPollock = this.ProtocoloPollockRepository
//				.findById(1000L)
//				.orElse(null);
//		
//		Assert.assertNotNull(ProtocoloPollock);
//		Assert.assertTrue(ProtocoloPollock.getAbdomen().equals(666d));
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
//		"/dataset/academia/avaliacaoFisica/protocolos/protocoloPollock.sql"
//	})
//	public void updateProtocoloPollockMustFail(){
//		
//		ProtocoloPollock ProtocoloPollock = this.ProtocoloPollockRepository
//				.findById(1000L)
//				.orElse(null);
//		
//		Assert.assertNotNull(ProtocoloPollock);
//		
//		ProtocoloPollock.setAbdomen(null);
//		ProtocoloPollock.setBracoDireitoRelaxado(null);
//		
//		this.protocoloPollockService.updateProtocoloPollock(ProtocoloPollock);
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
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloPollock.sql"
	})
	public void findProtocoloPollockMustPassById(){

		ProtocoloPollock ProtocoloPollock = this.protocoloPollockService
				.findProtocoloPollockById(1000L);
		
		Assert.assertNotNull(ProtocoloPollock);
		Assert.assertNotNull(ProtocoloPollock.getId());
		
	}
	
	/**
	 * Teste de busca de uma ProtocoloPollock por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/protocolos/protocoloPollock.sql"
	})
	public void findProtocoloPollockMustFailById(){
		
		this.protocoloPollockService
			.findProtocoloPollockById(151615615L);
		
	}
	
}
