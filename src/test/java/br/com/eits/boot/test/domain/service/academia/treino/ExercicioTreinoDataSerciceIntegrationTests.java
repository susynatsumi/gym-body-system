package br.com.eits.boot.test.domain.service.academia.treino;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioRealizado;
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
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			treinoData, 
			treinoExercicio
		);
		
		exercicioRealizado = this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioRealizado);
		
		Assert.assertNotNull(exercicioRealizado);
		Assert.assertNotNull(exercicioRealizado.getId());
		
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
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			null, // treino data 
			treinoExercicio
		);
		
		exercicioRealizado = this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioRealizado);
		
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
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			treinoData, 
			null
		);
		
		this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioRealizado);
		
	}
	
	
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exercicioTreinoData.sql"
	})
	public void insertExercicioTreinoDataMustFailUniqueTreinoDataIdTreinoExercicioId(){
		
		final TreinoData treinoData = new TreinoData(1000L);
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			treinoData, 
			null
		);
		
		this.exercicioTreinoDataService
				.insertExercicioTreinoData(exercicioRealizado);
		
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

		ExercicioRealizado exercicioRealizado = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioRealizado.setCompleto(true);

		exercicioTreinoDataService.updateExercicioTreinoData(exercicioRealizado);
		
		exercicioRealizado = this.exercicioTreinoDataRepository
			.findById(1000L)
			.orElse(null);
		
		Assert.assertNotNull(exercicioRealizado);
		Assert.assertTrue(exercicioRealizado.getCompleto());
		
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

		ExercicioRealizado exercicioRealizado = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioRealizado.setTreinoData(null);

		exercicioTreinoDataService.updateExercicioTreinoData(exercicioRealizado);
		
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

		ExercicioRealizado exercicioRealizado = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioRealizado.setTreinoExercicio(null);

		exercicioTreinoDataService.updateExercicioTreinoData(exercicioRealizado);
		
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
		
		ExercicioRealizado exercicioRealizado = this.exercicioTreinoDataRepository
				.findById(1000L)
				.orElse(null);

		exercicioRealizado.setTreinoExercicio(treinoExercicio);
		
		exercicioTreinoDataService.updateExercicioTreinoData(exercicioRealizado);
		
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
	
		final ExercicioRealizado exercicioRealizado = this.exercicioTreinoDataService
				.findExercicioTreinoDataById(1000L);

		
		Assert.assertNotNull(exercicioRealizado);
		Assert.assertNotNull(exercicioRealizado.getId());
	
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
	
