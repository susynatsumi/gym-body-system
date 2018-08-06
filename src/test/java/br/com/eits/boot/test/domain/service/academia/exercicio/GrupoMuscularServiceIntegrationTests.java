package br.com.eits.boot.test.domain.service.academia.exercicio;

import java.util.ArrayList;
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
	
	public List<Long> mockNotInt(){
		List<Long> ids = new ArrayList<Long>();
		ids.add(-9999999L);
		
		return ids;
	}
	
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

	/**
	 * Listagem de grupos musculares de acordo com o filtro de nome
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void listExercicioGrupoMuscularReturn2(){

		Page<GrupoMuscular> gruposMusculares = this.grupoMuscularService
				.listByFilters("grupo", mockNotInt() ,  null);
		
		Assert.assertEquals(2, gruposMusculares.getTotalElements());
		
	}
	
	/**
	 * Listagem de grupos musculares de acordo com o filtro de id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void listExercicioGrupoMuscularReturn1(){
		
		Page<GrupoMuscular> gruposMusculares = this.grupoMuscularService
				.listByFilters("1000", mockNotInt(), null);
		
		Assert.assertEquals(1, gruposMusculares.getTotalElements());
		
	}
	
	/**
	 * Listagem filtrando por um valor e utilizando o not in 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void listExercicioGrupoMuscularNotInReturn2(){
		
		List<Long> notIn = mockNotInt();
		notIn.add(1002L);
		
		Page<GrupoMuscular> gruposMusculares = this.grupoMuscularService
				.listByFilters("", notIn , null);
		
		Assert.assertEquals(2, gruposMusculares.getTotalElements());
		
	}
	
	// --------------------------------------------------
	// -------------------- DELETES ---------------------
	// --------------------------------------------------
	
	/**
	 * Teste de remoção com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/grupoMuscular.sql"
	})
	public void deleteGrupoMuscularMustPass(){

		this.grupoMuscularService.deleteGrupoMuscular(1000L);
		
		GrupoMuscular grupoMuscular = this.grupoMuscularRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNull(grupoMuscular);
		
	}
	
	/**
	 * Teste de remoção falha
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/exercicioGrupoMuscular.sql"
	})
	public void deleteGrupoMuscularMustFail(){

		this.grupoMuscularService.deleteGrupoMuscular(1000L);
		
	}
	
	
}
