package br.com.eits.boot.test.domain.service.academia.treino;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.treino.DiaSemana;
import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoDataRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoRepository;
import br.com.eits.boot.domain.service.academia.treino.TreinoDataService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class TreinoDataServiceIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private ITreinoDataRepository treinoDataRepository;
	
	@Autowired
	private TreinoDataService treinoDataService;
	
	@Autowired
	private ITreinoRepository treinoRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Inserção de um treino com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void insertTreinoDataMustPass(){

		final Treino treino = this.treinoRepository
				.findById(1001L)
				.orElse(null);

		TreinoData treinoData = new TreinoData(
			LocalDate.of(2018, 7, 19),
			LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)), 
			LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 00)), 
			false, 
			treino,
			DiaSemana.QUINTA
		);
		
		treinoData = this.treinoDataService.insertTreinoData(treinoData);
		
		Assert.assertNotNull(treinoData);
		Assert.assertNotNull(treinoData.getId());
		
		Assert.assertNotNull(treinoData.getHoraInicio());
		Assert.assertNotNull(treinoData.getHoraTermino());
		
		Assert.assertFalse(treinoData.getCompleto());
		
		
	}
	
	/**
	 * Valida obrigatoriedade do campo data
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void insertTreinoDataFailFailMandatoryFieldData(){
//FIXME problema lazy loading
		final Treino treino = new Treino(1001L);
		
		TreinoData treinoData = new TreinoData(
			null, // data
			LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)), 
			LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 00)), 
			true, 
			treino,
			DiaSemana.QUINTA
		);
		
		treinoData = this.treinoDataService.insertTreinoData(treinoData);
		
	}
	
	/**
	 * Valida obrigatoriedade do treino
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void insertTreinoDataFailFailMandatoryFieldTreinoId(){

		TreinoData treinoData = new TreinoData(
			LocalDate.of(2011, 5, 10), // data
			LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)), 
			LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 00)), 
			true, 
			null,
			DiaSemana.QUINTA
		);
		
		treinoData = this.treinoDataService.insertTreinoData(treinoData);
		
	}
	
	/**
	 * Valida unique de treino por data
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void insertTreinoDataFailFailUniqueDataTreinoId(){

		final Treino treino = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		TreinoData treinoData = new TreinoData(
			LocalDate.of(2018, 1, 1), // data
			LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)), 
			LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 00)), 
			true, 
			treino,
			DiaSemana.QUINTA
		);
		
		treinoData = this.treinoDataService.insertTreinoData(treinoData);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	 * Valida update de hora de início
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void updateTreinoDataPassHoraInicio(){
		
		TreinoData treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);
		
		LocalDateTime novaHora = LocalDateTime.of(LocalDate.now(), LocalTime.of(01, 1));
		
		treinoData.setHoraInicio(novaHora);
		
		this.treinoDataService.updateTreinoData(treinoData);
		
		treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);
		Assert.assertNotNull(treinoData.getId());
		
		Assert.assertEquals(novaHora, treinoData.getHoraInicio());
	}
	
	/**
	 * Valida update de hora de fim
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void updateTreinoDataPassHoraFim(){
		
		TreinoData treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);
		
		LocalDateTime novaHora = LocalDateTime.of(LocalDate.now(), LocalTime.of(01, 1));
		
		treinoData.setHoraTermino(novaHora);
		
		this.treinoDataService.updateTreinoData(treinoData);
		
		treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);
		Assert.assertNotNull(treinoData.getId());
		System.out.println(novaHora);
		System.out.println(treinoData.getHoraTermino());
		
		Assert.assertEquals(novaHora, treinoData.getHoraTermino());
	}
	
	/**
	 * Valida update de data, que não deve ocorrer
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void updateTreinoDataPassData(){
		
		TreinoData treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);

		LocalDate novaData = LocalDate.of(1000, 12, 13);
		
		treinoData.setData(novaData);
		
		this.treinoDataService.updateTreinoData(treinoData);
		
		treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);
		Assert.assertNotNull(treinoData.getId());

		Assert.assertNotEquals(novaData, treinoData.getData());
	}
	
	/**
	 * update do campo completo
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void updateTreinoDataPassMandatoryFieldCompleto(){
		
		TreinoData treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);

		treinoData.setCompleto(true);
		
		this.treinoDataService.updateTreinoData(treinoData);
		
		treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);
		Assert.assertNotNull(treinoData.getId());

		Assert.assertFalse(treinoData.getCompleto());
	}
	
	/**
	 * Teste de obrigatoriedade do campo treino
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void updateTreinoDataFailMandatoryFieldTreinoId(){
		
		TreinoData treinoData = this.treinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoData);

		treinoData.setTreino(null);
		
		this.treinoDataService.updateTreinoData(treinoData);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Find by id com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void findTreinoDataPassById(){

		final TreinoData treinoData = this.treinoDataService
				.findTreinoDataById(1002L);
		
		Assert.assertNotNull(treinoData);
		Assert.assertNotNull(treinoData.getId());
		
	}
	
	/**
	 * Testa o caso de não existir o id informado no find
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoData.sql"
	})
	public void findTreinoDataFailById(){

		this.treinoDataService
				.findTreinoDataById(1015655602L);
		
	}
	

	// LIST BY FILTERS 
	
//	/**
//	 * Realiza teste de filtragem por data e aluno
//	 */
//	@Test( )
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/treino/treinoData.sql"
//	})
//	public void listTreinoDataByFilters(){
//		
//		Page<TreinoData> treinosData = this.treinoDataService
//			.listTreinoDataByFilters(
//				LocalDate.of(2018, 9, 1), 
//				LocalDate.of(2018, 12, 1), 
//				1011L, 
//				null,
//				null
//		);
//		
//		Assert.assertNotNull(treinosData);
//		Assert.assertTrue( Long.valueOf(2).equals(treinosData.getTotalElements()));
//		
//	}
//	
//	/**
//	 * realiza teste da obrigatoriedade do parametro id aluno
//	 */
//	@Test( expected = IllegalArgumentException.class )
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/treino/treinoData.sql"
//	})
//	public void listTreinoDataByFiltersData(){
//		
//		this.treinoDataService
//			.listTreinoDataByFilters(
//				LocalDate.of(2018, 9, 1), 
//				LocalDate.of(2018, 10, 1), 
//				null,
//				null,
//				null
//		);
//		
//	}
//	
//	/**
//	 * Realiza validação da obrigatoriedade do parametro dataInicio
//	 */
//	@Test( expected = IllegalArgumentException.class )
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/treino/treinoData.sql"
//	})
//	public void listTreinoDataByFiltersIdAluno(){
//		
//		this.treinoDataService
//				.listTreinoDataByFilters(
//					null,
//					LocalDate.now(),
//					Long.valueOf(1001), 
//					null,
//					null
//				);
//		
//	}
//	
//	
//	/**
//	 * Realiza validação da obrigatoriedade do parametro dataTermino
//	 */
//	@Test( expected = IllegalArgumentException.class )
//	@WithUserDetails("admin@email.com")
//	@Sql({
//		"/dataset/pessoa/pessoas.sql",
//		"/dataset/academia/treino/treinoData.sql"
//	})
//	public void listTreinoDataTerminoByFiltersIdAluno(){
//		
//		this.treinoDataService
//				.listTreinoDataByFilters(
//					LocalDate.now(),
//					null,
//					Long.valueOf(1001), 
//					true,
//					null
//				);
//		
//	}
	
	
}
