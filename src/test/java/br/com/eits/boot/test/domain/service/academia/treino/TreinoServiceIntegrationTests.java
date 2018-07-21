package br.com.eits.boot.test.domain.service.academia.treino;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.Academia;
import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.academia.treino.DiaSemana;
import br.com.eits.boot.domain.entity.academia.treino.PessoaTreino;
import br.com.eits.boot.domain.entity.academia.treino.TipoTreinoExercicio;
import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.IAcademiaRepository;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoDataRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.treino.TreinoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class TreinoServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private TreinoService treinoService;
	
	@Autowired
	private ITreinoRepository treinoRepository;
	
	@Autowired
	private IAcademiaRepository academiaRepository;
	
	@Autowired
	private IExercicioRepository exercicioRepository;
	
	@Autowired
	private ITreinoDataRepository treinoDataRepository;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Cria treino exercicio para testes
	 * 
	 * @return
	 */
	private List<TreinoExercicio> mockTreinoExercicio(){
	
		final Exercicio exercicio1 = this.exercicioRepository
		.findById(1000L)
		.orElse(null);

		final Exercicio exercicio2 = this.exercicioRepository
		.findById(1001L)
		.orElse(null);
		
		return Arrays.asList(
			new TreinoExercicio(10, 11, null, "Faça devagar", null,  exercicio1, TipoTreinoExercicio.CARGA_REPETICOES),
			new TreinoExercicio(10, 11, null, "", null, exercicio2, TipoTreinoExercicio.CARGA_REPETICOES)
		);
	}
	
	/**
	 * Faz mock de dias da semana para criação do treino
	 * 
	 * @return
	 */
	private List<DiaSemana> mockDiasSemana(){
		return Arrays.asList(
			DiaSemana.SEGUNDA,
			DiaSemana.SEXTA
		);
	}
	
	/**
	 * Cria pessoas para inserção no treino
	 * @return
	 */
	private List<PessoaTreino> mockPersonalAluno(){
		Pessoa pessoa1 = this.pessoaRepository
				.findById(1013L)
				.orElse(null);
		
		Pessoa pessoa2 = this.pessoaRepository
				.findById(1011L)
				.orElse(null);
		
		PessoaTreino pessoaTreino1 = new PessoaTreino(pessoa1, null, Papel.PERSONAL);
		PessoaTreino pessoaTreino2 = new PessoaTreino(pessoa2, null, Papel.ALUNO);
		
		return Arrays.asList(
			pessoaTreino1,
			pessoaTreino2
		);
		
		
	}
	
	/**
	 * Inserção de um treino com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustPass(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			LocalDate.of(2018, 6, 1), 
			LocalDate.of(2018, 7, 1), 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			mockTreinoExercicio(), 
			mockDiasSemana(), 
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
		Assert.assertNotNull(treino);
		Assert.assertNotNull(treino.getId());
		Assert.assertEquals("Treino bla bla bla", treino.getNome());
		
		final Treino treinoInserido = this.treinoRepository
				.findById(treino.getId())
				.orElse(null);
		
		Assert.assertNotNull(treinoInserido);
		Assert.assertEquals(2, treinoInserido.getTreinoExercicios().size());
		
		List<TreinoData> datasTreino = this.treinoDataRepository
				.findByTreino_Id(treino.getId());
		
		Assert.assertNotNull(datasTreino);
		Assert.assertEquals(9, datasTreino.size());
		
	}
	
	/**
	 * Deve ocorer erro se a data de fim não for posterior a data de inicio
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustFailMandatoryFieldsDataInicioAndDataFim(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			LocalDate.of(2018, 7, 1), 
			LocalDate.of(2018, 7, 1), 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			mockTreinoExercicio(), 
			mockDiasSemana(), 
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
	}
	
	/**
	 * Valida obrigatoriedade do campo data de inicio
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustFailMandatoryFieldsDataInicio(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			null,// data inicio 
			LocalDate.of(2018, 7, 1),  // data fim 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			mockTreinoExercicio(), 
			mockDiasSemana(), 
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
	}
	
	/**
	 * Valida obrigatoriedade do campo data de fim
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustFailMandatoryFieldsDataFim(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			LocalDate.of(2018, 7, 1),  // data inicio 
			null,// data fim 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			mockTreinoExercicio(), 
			mockDiasSemana(), 
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
	}
	
	/**
	 * Verifica se a validação de quantidade de exericicios está ok
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustFailListExercicios(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			LocalDate.of(2018, 6, 1),  // data inicio 
			LocalDate.of(2018, 7, 1),  // data fim 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			null, 
			mockDiasSemana(), 
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
	}
	
	/**
	 * Verifica se a validação de quantidade de dias da semana está ok
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustFailListDiasSemana(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			LocalDate.of(2018, 6, 1),  // data inicio 
			LocalDate.of(2018, 7, 1),  // data fim 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			mockTreinoExercicio(),
			null,
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
	}
	
	
	/**
	 * Verifica se existe alguma data nos dias de semana selecionados
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void insertTreinoMustFailListDatasTreino(){

		final Academia academia = this.academiaRepository
				.findById(1001L)
				.orElse(null);
		
		Treino treino  = new Treino(
			"Treino bla bla bla", 
			LocalDate.of(2018, 6, 2),  // data inicio 
			LocalDate.of(2018, 6, 3),  // data fim 
			LocalTime.of(10, 0), 
			LocalTime.of(11, 00), 
			mockTreinoExercicio(),
			mockDiasSemana(),
			academia,
			mockPersonalAluno()
		);
		
		treino = this.treinoService
				.insertTreino(treino);
		
	}
	
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	
	/**
	 * Inserção de um treino com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void updateTreinoMustPass(){

		Treino treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);

		treino.setNome("NOme do treino ahahahah");
		
		this.treinoService.updateTreino(treino);
		
		treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);
		Assert.assertEquals("NOme do treino ahahahah", treino.getNome());
		
	}
	
	/**
	 * Não deve alterar data de incio do treino
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void updateTreinoMustPassMandatoryFieldsDataInicio(){

		Treino treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);

		treino.setDataInicio(LocalDate.of(2000, 5, 12));
		
		this.treinoService.updateTreino(treino);
		
		treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);
		Assert.assertNotEquals(LocalDate.of(2000, 5, 12), treino.getDataInicio());
		
	}
	
	/**
	 * O sistema não deverá alterar a data de fim do treino
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void updateTreinoMustPassMandatoryFieldsDataFim(){

		Treino treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);

		treino.setDataFim(LocalDate.of(2000, 5, 12));
		
		this.treinoService.updateTreino(treino);
		
		treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);
		Assert.assertNotEquals(LocalDate.of(2000, 5, 12), treino.getDataFim());
		
		
	}
	
	/**
	 *  O sistema não deverá alterar o nome para um texto invalido
	 */
	@Test( expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void updateTreinoMustFailMandatoryNome(){

		Treino treino  = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(treino);

		treino.setNome("");
		
		this.treinoService.updateTreino(treino);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------


	/**
	 * Deverá realizar o find by id com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void findTreinoMustPassById(){

		Treino treino  = this.treinoService
				.findTreinoById(1000L);
		
		Assert.assertNotNull(treino);
		Assert.assertNotNull(treino.getId());
		
	}
	
	/**
	 * Deverá ocorrer erro por não existir um registro com o id informado
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/treinos.sql"
	})
	public void findTreinoMustFailById(){

		this.treinoService
				.findTreinoById(10116500L);
		
	}
	
	//TODO fazer testes com filtros
	
}
