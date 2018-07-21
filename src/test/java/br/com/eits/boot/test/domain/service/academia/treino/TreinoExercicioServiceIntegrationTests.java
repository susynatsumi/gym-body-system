package br.com.eits.boot.test.domain.service.academia.treino;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;

import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.academia.treino.TipoTreinoExercicio;
import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoExercicioRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoRepository;
import br.com.eits.boot.domain.service.academia.treino.TreinoExercicioService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class TreinoExercicioServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private ITreinoExercicioRepository treinoExercicioRepository;
	
	@Autowired
	private TreinoExercicioService treinoExercicioService;
	
	@Autowired
	private ITreinoRepository treinoRepository;
	
	@Autowired
	private IExercicioRepository exercicioRepository;
	
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
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustPass(){

		final Treino treino = this.treinoRepository
				.findById(1001L)
				.orElse(null);
		
		final Exercicio exercicio = this.exercicioRepository
				.findById(1002L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			10, 
			165, 
			0, 
			"asdfsfdaf", 
			treino, 
			exercicio,
			TipoTreinoExercicio.CARGA_REPETICOES
		);
		
		treinoExercicio.setIsAtivo(false);
		
		treinoExercicio = this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
		Assert.assertNotNull(treinoExercicio);
		Assert.assertNotNull(treinoExercicio.getId());
		Assert.assertTrue(treinoExercicio.getIsAtivo());
	}
	
	/**
	 * Valida unique de treino e exericicio
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustFailUniqueExercicioTreino(){

		final Treino treino = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		final Exercicio exercicio = this.exercicioRepository
				.findById(1000L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			10, 
			165, 
			0, 
			"asdfsfdaf", 
			treino, 
			exercicio,
			TipoTreinoExercicio.CARGA_REPETICOES
		);
		
		treinoExercicio = this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
	}
	
	/**
	 * Valida campo de id treino not null 
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustFailForeignKeyTreino(){

		final Exercicio exercicio = this.exercicioRepository
				.findById(1002L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			0, // carga
			110, // repet
			0, // tempo
			"asdfsfdaf", 
			null, 
			exercicio,
			TipoTreinoExercicio.REPETICOES
		);
		
		treinoExercicio = this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
	}
	
	/**
	 * Valida campo not null de exercicio id
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustFailForeignKeyExercicio(){
		
		final Treino treino = new Treino(1001L);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			10, // carga 
			1, // repet
			0, // tempo min
			"asdfsfdaf", 
			treino, 
			null,
			TipoTreinoExercicio.CARGA_REPETICOES
		);
		
		treinoExercicio = this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
	}
	
	/**
	 * Valida insercacao de treino exercicio do tipo carga e repeticoes 
	 * com valores inválidos
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustFailMandatoryFieldCargaRepeticoes(){

		final Treino treino = this.treinoRepository
				.findById(1001L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			10, // carga 
			0, // repet
			0, // tempo min
			"asdfsfdaf", 
			treino, 
			null,
			TipoTreinoExercicio.CARGA_REPETICOES
		);
		
		this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
	}
	
	/*
	 * valida valores inválidos para o campo repeticoes para o tipo 
	 * repeticoes
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustFailMandatoryFieldRepeticoes(){

		final Treino treino = this.treinoRepository
				.findById(1001L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			0, // carga 
			0, // repet
			0, // tempo min
			"asdfsfdaf", 
			treino, 
			null,
			TipoTreinoExercicio.CARGA_REPETICOES
		);
		
		this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
	}
	
	/**
	 * Valida valores inválidos para o campo tempo em min
	 * quando o tipo do exercicio for por tempo
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void insertTreinoExercicioMustFailMandatoryFieldTempo(){

		final Treino treino = this.treinoRepository
				.findById(1001L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = new TreinoExercicio(
			0, // carga 
			0, // repets
			0, // tempo min
			"asdfsfdaf", 
			treino, 
			null,
			TipoTreinoExercicio.TEMPO
		);
		
		this.treinoExercicioService
				.insertTreinoExercicio(treinoExercicio);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Update realizado com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustPass(){

		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		
		treinoExercicio.setCarga(411);
		
		this.treinoExercicioService.updateTreinoExercicio(treinoExercicio);
		
		treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		Assert.assertNotNull(treinoExercicio.getId());
		
		Assert.assertEquals(Integer.valueOf(411), treinoExercicio.getCarga());
		
	}
	
	/**
	 * Testa o metodo de inativacao de exercicio do treino
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustPassInativar(){

		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		
		this.treinoExercicioService.inativarTreinoExercicio(treinoExercicio.getId());
		
		treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		Assert.assertNotNull(treinoExercicio.getId());
		Assert.assertNotNull(treinoExercicio.getDataInativacao());
		Assert.assertFalse(treinoExercicio.getIsAtivo());
		
	}
	
	/**
	 * Valida unique no update
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustFailUniqueTreinoExercicio(){

		final Exercicio exercicio = this.exercicioRepository
				.findById(1001L)
				.orElse(null);
		
		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		treinoExercicio.setExercicio(exercicio);
		
		this.treinoExercicioService.updateTreinoExercicio(treinoExercicio);
		
		
	}
	
	/**
	 * Verifica se o sistema permite dar update em exercicios do treino inativos
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustFailInativo(){

		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1002L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		
		treinoExercicio.setCarga(0);
		
		this.treinoExercicioService.updateTreinoExercicio(treinoExercicio);
		
	}
	
	/*
	 * Valida update para o tipo carga repeticoes
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustFailMandatoryFieldCarga(){

		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1002L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		
		treinoExercicio.setCarga(0);
		
		this.treinoExercicioService.updateTreinoExercicio(treinoExercicio);
		
	}
	
	/**
	 * Valida o tipo de exercicio repeticoes
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustFailMandatoryFieldRepeticoes(){

		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		
		treinoExercicio.setRepeticoes(0);
		
		this.treinoExercicioService.updateTreinoExercicio(treinoExercicio);
		
	}
	
	/**
	 * valida updata para o tipo tempo em min
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void updateTreinoExercicioMustFailMandatoryFieldTempo(){

		TreinoExercicio treinoExercicio = this.treinoExercicioRepository
				.findById(1001L)
				.orElse(null);
		
		Assert.assertNotNull(treinoExercicio);
		
		treinoExercicio.setTempoMin(0);
		
		this.treinoExercicioService.updateTreinoExercicio(treinoExercicio);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Busca por id feita com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void findTreinoExercicioMustPassById(){

		TreinoExercicio treinoExercicio = this.treinoExercicioService
				.findTreinoExercicioById(1000L);
		
		Assert.assertNotNull(treinoExercicio);
		Assert.assertNotNull(treinoExercicio.getId());
		
	}
	
	/**
	 * Busca por id inexistente
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinoExercicios.sql"
	})
	public void findTreinoExercicioMustFailById(){

		this.treinoExercicioService
				.findTreinoExercicioById(100056115L);
		
	}

	
	//TODO fazer testes com filtros
	
}
