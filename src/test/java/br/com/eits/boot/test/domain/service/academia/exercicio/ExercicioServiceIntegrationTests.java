package br.com.eits.boot.test.domain.service.academia.exercicio;

import java.util.Arrays;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.exercicio.Equipamento;
import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.academia.exercicio.ExercicioGrupoMuscular;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioGrupoMuscularRepository;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioRepository;
import br.com.eits.boot.domain.repository.academia.exercicio.IGrupoMuscularRepository;
import br.com.eits.boot.domain.service.academia.exercicio.ExercicioService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class ExercicioServiceIntegrationTests extends AbstractIntegrationTests{

	
	@Autowired
	private ExercicioService exercicioService;
	
	@Autowired
	private IGrupoMuscularRepository grupoMuscularRepository;
	
	@Autowired
	private IExercicioGrupoMuscularRepository exercicioGrupoMuscularRepository;
	
	@Autowired
	private IExercicioRepository exercicioRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Faz mock de grupos musuclares
	 * @return
	 */
	private List<ExercicioGrupoMuscular> mockExercicioGrupoMuscular(){
		
		return Arrays.asList(
			new ExercicioGrupoMuscular(null, this.grupoMuscularRepository.findById(1000L).orElse(null)),
			new ExercicioGrupoMuscular(null, this.grupoMuscularRepository.findById(1001L).orElse(null))
		);
	}
	
	/**
	 * Testa inserção com sucesso de um exercicio
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void insertExercicioMustPass(){

		Equipamento equipamento = new Equipamento(1000L);
		
		Exercicio exercicio = new Exercicio(
			"Blsalksadjfsa", 
			"sdfafçajfsçasakfsajklçasjfsalksajlkçasjflsajlkçasjflk"+ 
			"jsadlkjsadkljsalçjajjjajçjçjjjfajlksafjlkfsa", 
			null, 
			false, 
			equipamento
		);
		
		exercicio.setExercicioGrupoMusculares(mockExercicioGrupoMuscular());
		
		exercicio = this.exercicioService
				.insertExercicio(exercicio);
		
		Assert.assertNotNull(exercicio);
		Assert.assertNotNull(exercicio.getId());
		Assert.assertTrue(exercicio.getIsAtivo());
		
		Page<ExercicioGrupoMuscular> exercicioGrupoMuscular = this.exercicioGrupoMuscularRepository
				.findByExercicio_id(exercicio.getId() , null );
		
		Assert.assertEquals(2L, exercicioGrupoMuscular.getTotalElements());
		
	}
	
	/**
	 * Teste de inserção sem nenhum atributo populado
	 */
	@Test(expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void insertExercicioMustFail(){
		
		Exercicio exercicio = new Exercicio();
		
		exercicio.setExercicioGrupoMusculares(mockExercicioGrupoMuscular());
		
		exercicio = this.exercicioService
				.insertExercicio(exercicio);
	}
	
	/**
	 * Testa inserção de Exercício sem o nome
	 * Campo obrigatório
	 */
	@Test(expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void insertExercicioMustFailMandatoryFieldNome(){

		Equipamento equipamento = new Equipamento(1000L);
		
		Exercicio exercicio = new Exercicio(
			"", 
			"sdfafçajfsçasakfsajklçasjfsalksajlkçasjflsajlkçasjflk"+ 
			"jsadlkjsadkljsalçjajjjajçjçjjjfajlksafjlkfsa", 
			null, 
			false, 
			equipamento
		);
		
		exercicio.setExercicioGrupoMusculares(mockExercicioGrupoMuscular());
		
		exercicio = this.exercicioService
				.insertExercicio(exercicio);
	}
	
	/**
	 * Testa inserção de exercício sem equipamento
	 */
	@Test(expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void insertExercicioMustFailForeignKeyEquipamento(){
		
		Exercicio exercicio = new Exercicio(
			"Teste teste ", 
			"sdfafçajfsçasakfsajklçasjfsalksajlkçasjflsajlkçasjflk"+ 
			"jsadlkjsadkljsalçjajjjajçjçjjjfajlksafjlkfsa", 
			null, 
			false, 
			null
		);
		
		exercicio.setExercicioGrupoMusculares(mockExercicioGrupoMuscular());
		
		exercicio = this.exercicioService
				.insertExercicio(exercicio);
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Teste de update que deve ocorrer com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void updateExercicioMustPassMandatoryFieldNome(){
		
		Exercicio exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);
		
		exercicio.setNome("Exercicio Teste");
		
		this.exercicioService.updateExercicio(exercicio);
		
		exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(exercicio);
		Assert.assertNotNull(exercicio.getId());
		Assert.assertEquals("Exercicio Teste", exercicio.getNome());
	}
	
	/**
	 * Update de equipamento do exercicio 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void updateExercicioMustPassMandatoryFieldEquipamento(){
		
		Equipamento equipamentoNovo = new Equipamento(1000L);
		
		Exercicio exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);

		exercicio.setEquipamento(equipamentoNovo);
		
		this.exercicioService.updateExercicio(exercicio);
		
		exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(exercicio);
		Assert.assertNotNull(exercicio.getId());
		Assert.assertEquals(equipamentoNovo.getId(), exercicio.getEquipamento().getId());
	}
	
	
	/**
	 * Teste de update para exercicios, atualizando para um valor inválido
	 * o campo nome
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void updateExercicioMustFailMandatoryFieldNome(){
		
		Exercicio exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);
		
		exercicio.setNome("");
		
		this.exercicioService.updateExercicio(exercicio);
		
	}
	
	@Test( expected = DataIntegrityViolationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void updateExercicioMustFailMandatoryFieldEquipamento(){
		
		Exercicio exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);

		exercicio.setEquipamento(null);
		
		this.exercicioService.updateExercicio(exercicio);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Teste do find by id com registro existente
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void findExercicioMustPassFindById(){
		
		Exercicio exercicio = this.exercicioService
				.findExercicioById(1000L);

		Assert.assertNotNull(exercicio);
		Assert.assertNotNull(exercicio.getId());
		Assert.assertNotNull(exercicio.getNome());
		
	}
	
	/**
	 * Teste do find com id inexistente
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void findExercicioMustFailFindById(){
		
		this.exercicioService.findExercicioById(1056165600L);

	}

	/**
	 * Valida filtro de busca
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void findExercicioMustReturn2(){

		Page<Exercicio> exercicios = exercicioService
				.listExerciciosByFilters(
					"Corrida", 
					null,
					null
				);
		
		Assert.assertNotNull(exercicios);
		Assert.assertEquals(2L, exercicios.getTotalElements());

	}

	/**
	 * Teste de busca pelo filtro de status
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void findExercicioMustReturn1(){

		Page<Exercicio> exercicios = exercicioService
				.listExerciciosByFilters(
					null,
					false,
					null
				);
		
		Assert.assertNotNull(exercicios);
		Assert.assertEquals(1L, exercicios.getTotalElements());

	}
	
	/**
	 * Teste de remoção de exercicio com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicios.sql"
	})
	public void deleteExercicioMustPass() {
		
		this.exercicioService.deleteExercicio(1000L);

		Exercicio exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);
		

		Assert.assertNull(exercicio);
		
	}
	
	/**
	 * Teste de falha de remoção quando ouver um treino vinculado
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void deleteExercicioMustFail() {
		
		this.exercicioService.deleteExercicio(1000L);

		
	}
	
	
	
}
