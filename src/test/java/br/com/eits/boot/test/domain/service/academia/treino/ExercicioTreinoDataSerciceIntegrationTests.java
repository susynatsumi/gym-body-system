package br.com.eits.boot.test.domain.service.academia.treino;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioTreinoData;
import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;
import br.com.eits.boot.domain.repository.academia.treino.IExercicioTreinoDataRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoDataRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoExercicioRepository;
import br.com.eits.boot.domain.service.academia.treino.ExercicioTreinoDataService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class ExercicioTreinoDataSerciceIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private IExercicioTreinoDataRepository exercicioTreinoDataRepository;
	
	@Autowired
	private ExercicioTreinoDataService exercicioTreinoDataService;
	
	@Autowired
	private ITreinoDataRepository treinoDataRepository;
	
	@Autowired
	private ITreinoExercicioRepository treinoExercicioRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Insere uma pessoa treino com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void insertExercicioTreinoDataMustPass(){
		
		final TreinoData treinoData = this.treinoDataRepository
				.findById(1002L)
				.orElse(null);
		
		final TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		ExercicioTreinoData exercicioTreinoData = new ExercicioTreinoData(
			true, 
			treinoData, 
			treinoExercicio
		);
		
		exercicioTreinoData = this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioTreinoData);
		
		Assert.assertNotNull(exercicioTreinoData);
		Assert.assertNotNull(exercicioTreinoData.getId());
		
	}
	
	/**
	 * Valida foreign key Treino data
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void insertExercicioTreinoDataMustFailForeignKeyTreinoData(){
		
		final TreinoExercicio treinoExercicio = new TreinoExercicio(1000L);
		
		ExercicioTreinoData exercicioTreinoData = new ExercicioTreinoData(
			true, 
			null, // treino data 
			treinoExercicio
		);
		
		exercicioTreinoData = this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioTreinoData);
		
	}
	
	/**
	 * valida obrigatoriedade de treino exercicio
	 */
	@Test( expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void insertExercicioTreinoDataMustFailForeignKeyTreinoExercicio(){
		
		final TreinoData treinoData = new TreinoData(1002L);
		
		ExercicioTreinoData exercicioTreinoData = new ExercicioTreinoData(
			true, 
			treinoData, 
			null
		);
		
		this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioTreinoData);
		
	}
	
	
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void insertExercicioTreinoDataMustFailUniqueTreinoDataIdTreinoExercicioId(){
		
		final TreinoData treinoData = new TreinoData(1000L);
		
		ExercicioTreinoData exercicioTreinoData = new ExercicioTreinoData(
			true, 
			treinoData, 
			null
		);
		
		this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioTreinoData);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Valida update com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void updateExercicioTreinoDataMustPass(){

		ExercicioTreinoData exercicioTreinoData = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioTreinoData.setCompleto(true);

		exercicioTreinoDataService.updateExercicioTreinoData(exercicioTreinoData);
		
		exercicioTreinoData = this.exercicioTreinoDataRepository
			.findById(1000L)
			.orElse(null);
		
		Assert.assertNotNull(exercicioTreinoData);
		Assert.assertTrue(exercicioTreinoData.getCompleto());
		
	}
	
	/**
	 * Valida obrigatorieadade do campo exercicioreinoDataId
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void updateExercicioTreinoDataMustFailForeignKeyTreinoData(){

		ExercicioTreinoData exercicioTreinoData = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioTreinoData.setTreinoData(null);

		exercicioTreinoDataService.updateExercicioTreinoData(exercicioTreinoData);
		
	}
	
	/**
	 * Valida obrigatorieadade do campo treinoExercici
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void updateExercicioTreinoDataMustFailForeignKeyTreinoExercicio(){

		ExercicioTreinoData exercicioTreinoData = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioTreinoData.setTreinoExercicio(null);

		exercicioTreinoDataService.updateExercicioTreinoData(exercicioTreinoData);
		
	}
	
	/**
	 * Valida unique no update
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void updateExercicioTreinoDataMustFailUniqueTreinoDataTreinoExercicio(){

		
		final TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1001L)
				.orElse(null);
		
		ExercicioTreinoData exercicioTreinoData = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);

		exercicioTreinoData.setTreinoExercicio(treinoExercicio);
		
		exercicioTreinoDataService.updateExercicioTreinoData(exercicioTreinoData);
		
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
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void findExercicioTreinoDataMustPassById(){
	
		final ExercicioTreinoData exercicioTreinoData = this.exercicioTreinoDataService
				.findExercicioTreinoDataById(1000L);

		
		Assert.assertNotNull(exercicioTreinoData);
		Assert.assertNotNull(exercicioTreinoData.getId());
	
		//TODO fazer testes com filtros
	
	}
	
	/**
	 * 
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void findExercicioTreinoDataMustFailById(){
	
		this.exercicioTreinoDataService
				.findExercicioTreinoDataById(100165160L);
	}

	//TODO fazer testes com filtros
	
}
	
