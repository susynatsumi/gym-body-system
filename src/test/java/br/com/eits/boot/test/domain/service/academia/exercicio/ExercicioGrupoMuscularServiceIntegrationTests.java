package br.com.eits.boot.test.domain.service.academia.exercicio;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.academia.exercicio.ExercicioGrupoMuscular;
import br.com.eits.boot.domain.entity.academia.exercicio.GrupoMuscular;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioGrupoMuscularRepository;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioRepository;
import br.com.eits.boot.domain.repository.academia.exercicio.IGrupoMuscularRepository;
import br.com.eits.boot.domain.service.academia.exercicio.ExercicioGrupoMuscularService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class ExercicioGrupoMuscularServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private IExercicioGrupoMuscularRepository exercicioGrupoMuscularRepository;
	
	@Autowired
	private ExercicioGrupoMuscularService exercicioGrupoMuscularService;
	
	@Autowired
	private IGrupoMuscularRepository grupoMuscularRepository;
	
	@Autowired
	private IExercicioRepository exercicioRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Inserção de um grupo muscular para um exercicio
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void insertExercicioGrupoMuscularMustPass(){
		
		final Exercicio exercicio = new Exercicio(1002L);
		
		final GrupoMuscular grupoMuscular = new GrupoMuscular(1002L);
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = new 
				ExercicioGrupoMuscular(exercicio, grupoMuscular);

		exercicioGrupoMuscular = this.exercicioGrupoMuscularService
				.insertExercicioGrupoMuscular(exercicioGrupoMuscular);
		
		Assert.assertNotNull(grupoMuscular);
		Assert.assertNotNull(grupoMuscular.getId());
		
	}
	
	/**
	 * Teste insert de grupo muscular para exerciios com dados inválidos
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void insertExercicioGrupoMuscularMustFail(){
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = new
				ExercicioGrupoMuscular();

		exercicioGrupoMuscular = this.exercicioGrupoMuscularService
				.insertExercicioGrupoMuscular(exercicioGrupoMuscular);
		
	}
	
	/**
	 * Valida a unique key de grupo muscular e exercicio
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void insertExercicioGrupoMuscularMustFailUniqueConstraintGrupoExercicio(){
		
		final Exercicio exercicio = new Exercicio(1000L);
		
		final GrupoMuscular grupoMuscular = new GrupoMuscular(1000L);
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = new
				ExercicioGrupoMuscular(exercicio, grupoMuscular);

		exercicioGrupoMuscular = this.exercicioGrupoMuscularService
				.insertExercicioGrupoMuscular(exercicioGrupoMuscular);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Update de um exercicio grupo muscular com sucesso atribuindo um novo exercicio
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void udpateExercicioGrupoMuscularMustPassMandatoryFieldExercicio(){
		
		final Exercicio exercicio = this.exercicioRepository
				.findById(1002L)
				.orElse(null);
		
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular.setExercicio(exercicio);
		
		this.exercicioGrupoMuscularService
			.updateExercicioGrupoMuscular(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(exercicioGrupoMuscular);
		Assert.assertNotNull(exercicioGrupoMuscular.getId());
		Assert.assertEquals(exercicio.getId(), exercicioGrupoMuscular.getExercicio().getId());
	}
	
	/**
	 * Update atribuindo um novo grupo muscular
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void udpateExercicioGrupoMuscularMustPassMandatoryFieldGrupoMuscular(){
		
		final GrupoMuscular grupoMuscular = this.grupoMuscularRepository
				.findById(1002L)
				.orElse(null);
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular.setGrupoMuscular(grupoMuscular);
		
		this.exercicioGrupoMuscularService
			.updateExercicioGrupoMuscular(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(exercicioGrupoMuscular);
		Assert.assertNotNull(exercicioGrupoMuscular.getId());
		Assert.assertEquals(grupoMuscular.getId(), exercicioGrupoMuscular.getGrupoMuscular().getId());
	}
	
	/**
	 * Update setando o grupo muscular para null, deve ocorrer erro 
	 */
	@Test( expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void udpateExercicioGrupoMuscularMustFailMandatoryFieldGrupoMuscular(){
		
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular.setGrupoMuscular(null);
		
		this.exercicioGrupoMuscularService
			.updateExercicioGrupoMuscular(exercicioGrupoMuscular);
		
	}
	
	/**
	 * Update setando exercicio para null, deve ocorrer erro
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void udpateExercicioGrupoMuscularMustFailMandatoryFieldExercicio(){
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular.setExercicio(null);
		
		this.exercicioGrupoMuscularService
			.updateExercicioGrupoMuscular(exercicioGrupoMuscular);
		
	}
	
	/**
	 * Teste para verificar violação da unique
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void udpateExercicioGrupoMuscularMustFailUniqueExercicioGrupoMuscular(){
		
		final GrupoMuscular grupoMuscular = new GrupoMuscular(1001L);
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(exercicioGrupoMuscular);
		
		exercicioGrupoMuscular.setGrupoMuscular(grupoMuscular);
		
		this.exercicioGrupoMuscularService
			.updateExercicioGrupoMuscular(exercicioGrupoMuscular);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Testa o find by id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void findExercicioGrupoMuscularMustPassById(){
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this.exercicioGrupoMuscularService
				.findExercicioGrupoMuscularById(1000L);
		
		Assert.assertNotNull(exercicioGrupoMuscular);
		Assert.assertNotNull(exercicioGrupoMuscular.getId());
		
	}
	
	/**
	 * Find com um id inexistente
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void findExercicioGrupoMuscularMustFailById(){
		
		this.exercicioGrupoMuscularService
				.findExercicioGrupoMuscularById(10015160L);

	}
	
	
	/**
	 * Find com um id inexistente
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void deleteExercicioGrupoMuscularMustPass(){

		this.exercicioGrupoMuscularService.deleteById(1000L);
		
		ExercicioGrupoMuscular exercicioGrupoMuscular = this
				.exercicioGrupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		
		Assert.assertNull(exercicioGrupoMuscular);
		

	}
	
	
	//TODO fazer testes com filtros
	
}
