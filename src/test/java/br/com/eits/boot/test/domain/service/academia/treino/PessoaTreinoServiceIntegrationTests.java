package br.com.eits.boot.test.domain.service.academia.treino;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.treino.PessoaTreino;
import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.treino.IPessoaTreinoRepository;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.treino.PessoaTreinoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class PessoaTreinoServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private PessoaTreinoService pessoaTreinoService;
	
	@Autowired
	private IPessoaTreinoRepository pessoaTreinoRepository;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	@Autowired
	private ITreinoRepository treinoRepository;
	
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
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void insertPessoaTreinoMustPass(){
		
		final Pessoa pessoa = this.pessoaRepository
				.findById(1013L)
				.orElse(null);
		
		final Treino treino = this.treinoRepository
				.findById(1002L)
				.orElse(null);
		
		PessoaTreino pessoaTreino = new PessoaTreino(
			pessoa, 
			treino, 
			pessoa.getPapel() 
		);
		
		pessoaTreino = this.pessoaTreinoService.insertPessoaTreino(pessoaTreino);
		
		Assert.assertNotNull(pessoaTreino);
		Assert.assertNotNull(pessoaTreino.getId());
		
	}
	
	/**
	 * Valida obrigatoriedade de pessoa id
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void insertPessoaTreinoMustFailForeignKeyPessoa(){
		
		final Treino treino = new Treino(1002L);
		
		PessoaTreino pessoaTreino = new PessoaTreino(
			null, 
			treino, 
			Papel.PERSONAL 
		);
		
		pessoaTreino = this.pessoaTreinoService.insertPessoaTreino(pessoaTreino);
		
	}
	/**
	 * Valida obrigatoriedade do campo treino_id 
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void insertPessoaTreinoMustFailForeignKeyTreino(){
		
		final Pessoa pessoa = this.pessoaRepository
				.findById(1013L)
				.orElse(null);
		
		PessoaTreino pessoaTreino = new PessoaTreino(
			null, 
			null, 
			pessoa.getPapel() 
		);
		
		pessoaTreino = this.pessoaTreinoService.insertPessoaTreino(pessoaTreino);
		
	}
	
	/**
	 * Valida unique de papel por treino
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void insertPessoaTreinoMustFailUniquePapelTreino(){
		
		final Pessoa pessoa = this.pessoaRepository
				.findById(1013L)
				.orElse(null);
		
		final Treino treino = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		PessoaTreino pessoaTreino = new PessoaTreino(
			pessoa, 
			treino, 
			Papel.ADMINISTRATOR 
		);
		
		pessoaTreino = this.pessoaTreinoService.insertPessoaTreino(pessoaTreino);
		
	}
	
	/**
	 * Valida unique de pessoa, treino e papel
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void insertPessoaTreinoMustFailUniquePessoaTreinoPapel(){
		
		final Pessoa pessoa = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		final Treino treino = this.treinoRepository
				.findById(1000L)
				.orElse(null);
		
		PessoaTreino pessoaTreino = new PessoaTreino(
			pessoa, 
			treino, 
			Papel.PERSONAL 
		);
		
		pessoaTreino = this.pessoaTreinoService.insertPessoaTreino(pessoaTreino);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Update com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void updatePessoaTreinoMustPass(){

		PessoaTreino pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);
		
		pessoaTreino.setPapel(Papel.ALUNO);
		
		this.pessoaTreinoService.updatePessoaTreino(pessoaTreino);
		
		pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);
		Assert.assertNotNull(pessoaTreino.getId());
		Assert.assertEquals(Papel.ALUNO, pessoaTreino.getPapel());
		
	}
	
	/**
	 * Valida obrigatoriedade do campo papel
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void updatePessoaTreinoMustFailMandatoryFieldPapel(){

		PessoaTreino pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);
		
		pessoaTreino.setPapel(null);
		
		this.pessoaTreinoService.updatePessoaTreino(pessoaTreino);
		
	}
	
	/**
	 * Valida obrigatoriedade do campo pessoa id
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void updatePessoaTreinoMustFailForeignKeyPessoaId(){

		PessoaTreino pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);

		pessoaTreino.setPessoa(null);
		
		this.pessoaTreinoService.updatePessoaTreino(pessoaTreino);
		
	}
	
	/**
	 * Valida obrigatoriedade do campo treino_id
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void updatePessoaTreinoMustFailForeignKeyTreinoId(){

		PessoaTreino pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);

		pessoaTreino.setTreino(null);
		
		this.pessoaTreinoService.updatePessoaTreino(pessoaTreino);
		
	}
	
	/**
	 * Valida unique papel treino
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void updatePessoaTreinoMustFailUniquePapelTreinoId(){

		PessoaTreino pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);

		pessoaTreino.setPapel(Papel.PERSONAL);
		
		this.pessoaTreinoService.updatePessoaTreino(pessoaTreino);
		
	}
	
	/**
	 * Valida unique key pessoa, treino, papel
	 */
	@Test( expected = DataIntegrityViolationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void updatePessoaTreinoMustFailUniquePapelTreinoIdPessoaId(){

		Pessoa pessoa = this.pessoaRepository
				.findById(1011L)
				.orElse(null);
		
		PessoaTreino pessoaTreino = this.pessoaTreinoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(pessoaTreino);
		
		pessoaTreino.setPessoa(pessoa);
		pessoaTreino.setPapel(Papel.PERSONAL);
		
		this.pessoaTreinoService.updatePessoaTreino(pessoaTreino);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Busca de pessoa com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void findPessoaTreinoMustPassById(){

		PessoaTreino pessoaTreino = this.pessoaTreinoService
				.findPessoaTreinoById(1000L);
		
		Assert.assertNotNull(pessoaTreino);
		Assert.assertNotNull(pessoaTreino.getId());
		
	}
	
	/**
	 * Deve ocorrer erro com ao buscar por um id inexistente
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/treino/pessoaTreinos.sql"
	})
	public void findPessoaTreinoMustFailById(){

		this.pessoaTreinoService
				.findPessoaTreinoById(11489191000L);
		
	}
	
	//TODO fazer testes com filtros
	
}
