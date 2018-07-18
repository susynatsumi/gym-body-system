package br.com.eits.boot.test.domain.service.academia.exercicio;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.exercicio.GrupoMuscular;
import br.com.eits.boot.domain.repository.academia.exercicio.IGrupoMuscularRepository;
import br.com.eits.boot.domain.service.academia.exercicio.GrupoMuscularService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class GrupoMuscularServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private GrupoMuscularService grupoMuscularService;
	
	@Autowired
	private IGrupoMuscularRepository grupoMuscularRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Inserção com sucesso de um grupo muscular
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void insertExercicioGrupoMuscularMustPass(){
		
		GrupoMuscular grupoMuscular = new GrupoMuscular("Pernas bla bla bla", "");
		
		grupoMuscular = this.grupoMuscularService
				.insertGrupoMuscular(grupoMuscular);
		
		Assert.assertNotNull(grupoMuscular);
		Assert.assertNotNull(grupoMuscular.getId());
		
	}
		
	/**
	 * Inserção com falha, devido ao valor inválido para o campo nome
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void insertExercicioGrupoMuscularMustFailMandatoryFieldNome(){
		
		GrupoMuscular grupoMuscular = new GrupoMuscular("", "Teste teste teste");
		
		grupoMuscular = this.grupoMuscularService
				.insertGrupoMuscular(grupoMuscular);
		
	}
	
	/**
	 * Inserção com falha, devido a violação da unique
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void insertExercicioGrupoMuscularMustFailUniqueNome(){
		
		GrupoMuscular grupoMuscular = new GrupoMuscular("Grupo superior", "");
		
		grupoMuscular = this.grupoMuscularService
				.insertGrupoMuscular(grupoMuscular);
		
	}
	
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Update de um grupo muscular com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void updateExercicioGrupoMuscularMustPass(){
		
		GrupoMuscular grupoMuscular = this.grupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(grupoMuscular);
		
		String descricao = "Descrição aaaabbbcccccccc";
		
		grupoMuscular.setDescricao(descricao);
		
		this.grupoMuscularService.updateGrupoMuscular(grupoMuscular);
		
		grupoMuscular = this.grupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(grupoMuscular);
		Assert.assertNotNull(grupoMuscular.getId());
		Assert.assertEquals(descricao, grupoMuscular.getDescricao());
		
	}
	
	/**
	 * Falha no update com nome inválido para o grupo muscular
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void updateExercicioGrupoMuscularMustFailMandatoryFieldNome(){
		
		GrupoMuscular grupoMuscular = this.grupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(grupoMuscular);
		
		grupoMuscular.setNome("");
		
		this.grupoMuscularService.updateGrupoMuscular(grupoMuscular);
		
	}
	
	/**
	 * Testa a unique de nome
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void updateExercicioGrupoMuscularMustFailUniqueNome(){
		
		GrupoMuscular grupoMuscular = this.grupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(grupoMuscular);
		
		grupoMuscular.setNome("Costas");
		
		this.grupoMuscularService.updateGrupoMuscular(grupoMuscular);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Verifica se o serviço de buscar por id está ok
	 */
	@Test 
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void findExercicioGrupoMuscularMustPassById(){
		
		GrupoMuscular grupoMuscular = this.grupoMuscularService
				.findGrupoMuscularById(1001L);
		
		Assert.assertNotNull(grupoMuscular);
		Assert.assertNotNull(grupoMuscular.getId());
		
	}
	
	/**
	 * Teste do find by id com registro inexistente
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void findExercicioGrupoMuscularMustFailById(){
		
		this.grupoMuscularService.findGrupoMuscularById(100516551L);
		
	}
	
	//TODO fazer testes com filtros
	
}
