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
import br.com.eits.boot.domain.repository.academia.treino.IExercicioRealizadoRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoDataRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoExercicioRepository;
import br.com.eits.boot.domain.service.academia.treino.ExercicioRealizadoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class ExercicioRealizadoSerciceIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private IExercicioRealizadoRepository exercicioRealizadoRepository;
	
	@Autowired
	private ExercicioRealizadoService exercicioRealizadoService;
	
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
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void insertExercicioRealizadoMustPass(){
		
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
		
		exercicioRealizado = this.exercicioRealizadoService
				.insertExercicioRealizado(exercicioRealizado);
		
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
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void insertExercicioRealizadoMustFailForeignKeyTreinoData(){
		
		final TreinoExercicio treinoExercicio = new TreinoExercicio(1000L);
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			null, // treino data 
			treinoExercicio
		);
		
		exercicioRealizado = this.exercicioRealizadoService
				.insertExercicioRealizado(exercicioRealizado);
		
	}
	
	/**
	 * valida obrigatoriedade de treino exercicio
	 */
	@Test( expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void insertExercicioRealizadoMustFailForeignKeyTreinoExercicio(){
		
		final TreinoData treinoData = new TreinoData(1002L);
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			treinoData, 
			null
		);
		
		this.exercicioRealizadoService
				.insertExercicioRealizado(exercicioRealizado);
		
	}
	
	
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void insertExercicioRealizadoMustFailUniqueTreinoDataIdTreinoExercicioId(){
		
		final TreinoData treinoData = new TreinoData(1000L);
		
		ExercicioRealizado exercicioRealizado = new ExercicioRealizado(
			true, 
			treinoData, 
			null
		);
		
		this.exercicioRealizadoService
				.insertExercicioRealizado(exercicioRealizado);
		
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
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void updateExercicioRealizadoMustPass(){

		ExercicioRealizado exercicioRealizado = this.exercicioRealizadoRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioRealizado.setCompleto(true);

		exercicioRealizadoService.updateExercicioRealizado(exercicioRealizado);
		
		exercicioRealizado = this.exercicioRealizadoRepository
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
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void updateExercicioRealizadoMustFailForeignKeyTreinoData(){

		ExercicioRealizado exercicioRealizado = this.exercicioRealizadoRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioRealizado.setTreinoData(null);

		exercicioRealizadoService.updateExercicioRealizado(exercicioRealizado);
		
	}
	
	/**
	 * Valida obrigatorieadade do campo treinoExercici
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void updateExercicioRealizadoMustFailForeignKeyTreinoExercicio(){

		ExercicioRealizado exercicioRealizado = this.exercicioRealizadoRepository
				.findById(1000L)
				.orElse(null);
		
		exercicioRealizado.setTreinoExercicio(null);

		exercicioRealizadoService.updateExercicioRealizado(exercicioRealizado);
		
	}
	
	/**
	 * Valida unique no update
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void updateExercicioRealizadoMustFailUniqueTreinoDataTreinoExercicio(){

		
		final TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1001L)
				.orElse(null);
		
		ExercicioRealizado exercicioRealizado = this.exercicioRealizadoRepository
				.findById(1000L)
				.orElse(null);

		exercicioRealizado.setTreinoExercicio(treinoExercicio);
		
		exercicioRealizadoService.updateExercicioRealizado(exercicioRealizado);
		
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
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void findExercicioRealizadoMustPassById(){
	
		final ExercicioRealizado exercicioRealizado = this.exercicioRealizadoService
				.findExercicioRealizadoById(1000L);

		
		Assert.assertNotNull(exercicioRealizado);
		Assert.assertNotNull(exercicioRealizado.getId());
	
	}
	
	/**
	 * 
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/exerciciosRealizados.sql"
	})
	public void findExercicioRealizadoMustFailById(){
	
		this.exercicioRealizadoService
				.findExercicioRealizadoById(100165160L);
	}
	
}
	
