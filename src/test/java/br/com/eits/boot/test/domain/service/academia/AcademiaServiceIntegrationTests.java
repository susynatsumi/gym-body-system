package br.com.eits.boot.test.domain.service.academia;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.Academia;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.IAcademiaRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.AcademiaService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class AcademiaServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private IAcademiaRepository academiaRepository;
	
	@Autowired
	private AcademiaService academiaService;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	// -----------------------------------------
	// -------------- INSERTS ------------------
	// -----------------------------------------
	
	/**
	 * Valida inserção de uma nova academia 
	 * e se ela está ativa.
	 * 
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void insertAcademiaMustPass(){
		
		final Pessoa pessoaProprietario = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		final Academia academia = new Academia(
			"razaoSocial", 
			"nomeFantasia", 
			"02.105.376/0001-96", 
			"98466557788", 
			"Rua dos feijões 13",
			"11668000", 
			false,
			"CATANDUBAS",
			pessoaProprietario
		);

		final Academia inserida = this.academiaService.insertAcademia(academia);
		
		Assert.assertNotNull(inserida);
		Assert.assertNotNull(inserida.getId());
		Assert.assertEquals("02.105.376/0001-96", "02.105.376/0001-96");
		Assert.assertTrue(inserida.getIsAtiva());
		
	}
	
	/**
	 * inserção de academia com dados inválidos
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void insertAcademiaMustFail(){
		
		final Academia academia = new Academia();

		this.academiaService.insertAcademia(academia);
	}

	/**
	 * inserção de academia se nome fantasia
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void insertAcademiaMustFailMandatoryFieldNomeFantasia(){
		
		final Pessoa pessoaProprietario = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		final Academia academia = new Academia(
				"razaoSocial", 
				null, 
				"02.105.376/0001-96", 
				"98466557788", 
				"Rua dos feijões 13",
				"11668000", 
				false,
				"CATANDUBAS",
				pessoaProprietario
			);

		this.academiaService.insertAcademia(academia);
	}
	
	/*
	 * insert sem razão social
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void insertAcademiaMustFailMandatoryFieldRazaoSocial(){
		
		final Pessoa pessoaProprietario = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		final Academia academia = new Academia(
				null, 
				"Nome Fantasia", 
				"02.105.376/0001-96", 
				"98466557788", 
				"Rua dos feijões 13",
				"11668000", 
				false,
				"CATANDUBAS",
				pessoaProprietario
				);
		
		this.academiaService.insertAcademia(academia);
	}
	
	/**
	 * Insert sem cnpj
	 */
	@Test(expected = DataIntegrityViolationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void insertAcademiaMustFailMandatoryFieldCnpj(){
		
		final Pessoa pessoaProprietario = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		final Academia academia = new Academia(
				"Teste", 
				"Nome Fantasia", 
				null, 
				"98466557788", 
				"Rua dos feijões 13",
				"11668000", 
				false,
				"CATANDUBAS",
				pessoaProprietario
				);
		
		this.academiaService.insertAcademia(academia);
	}
	
	/**
	 * insert para testar a unique do cnpj
	 */
	@Test(expected = DataIntegrityViolationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void insertAcademiaMustFailUniqueCnpj(){
		
		final Pessoa pessoaProprietario = this.pessoaRepository
				.findById(1012L)
				.orElse(null);
		
		final Academia academia = new Academia(
				"Teste", 
				"Nome Fantasia", 
				"43.576.756/0001-96", 
				"98466557788", 
				"Rua dos feijões 13",
				"11668000", 
				false,
				"CATANDUBAS",
				pessoaProprietario
				);
		
		this.academiaService.insertAcademia(academia);
	}
	
	// ----------------------------------------------
	// --------------- UPDATES ----------------------
	// ----------------------------------------------
	
	/**
	 * Update com sucesso do campo razão social da academia
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void updateAcademiaMustPassMandatoryFieldRazaoSocial(){

		Academia academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(academia);
		
		academia.setRazaoSocial("TESTEAAA");
		
		this.academiaService.updateAcademia(academia);
		
		academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(academia);
		Assert.assertEquals("TESTEAAA", academia.getRazaoSocial());
		
	}
	
	/**
	 * Update com sucesso do campo cnpj da academia
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void updateAcademiaMustPassMandatoryFieldCnpj(){

		Academia academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(academia);
		
		academia.setCnpj("24.818.556/0001-58");
		
		this.academiaService.updateAcademia(academia);
		
		academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);

		Assert.assertNotNull(academia);
		Assert.assertEquals("24.818.556/0001-58", academia.getCnpj());
		
		
	}
	
	/**
	 * Valida obrigatoriedade do campo razão social
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void updateAcademiaMustFailMandatoryFieldRazaoSocial(){

		Academia academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(academia);
		
		academia.setRazaoSocial("");
		
		this.academiaService.updateAcademia(academia);
		
	}
	
	/**
	 * Valida obrigatoriedade do campo nome fantasia
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void updateAcademiaMustFailMandatoryFieldNomeFantasia(){

		Academia academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(academia);
		
		academia.setNomeFantasia("");
		
		this.academiaService.updateAcademia(academia);
		
	}
	
	/**
	 * valida obrigatoriedade do campo cnpj
	 */
	@Test(expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void updateAcademiaMustFailMandatoryFieldCnpj(){

		Academia academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(academia);
		
		academia.setCnpj("");
		
		this.academiaService.updateAcademia(academia);
		
	}
	
	/**
	 * Valida unique do campo cnpj
	 */
	@Test(expected = DataIntegrityViolationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void updateAcademiaMustFailUniqueCnpj(){

		Academia academia = this.academiaRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(academia);
		
		academia.setCnpj("34.287.463/0001-06");
		
		this.academiaService.updateAcademia(academia);
		
		
	}
	
	// --------------------------------
	// ----- FINDS --------------------
	// --------------------------------

	/**
	 * Teste de find by id com id Existente
	 */
	@Test()
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void findAcademiaMustPassById(){

		Academia academia = this.academiaService
				.findAcdemiaById(1000L);
		
		Assert.assertNotNull(academia);
		Assert.assertFalse(academia.getRazaoSocial().isEmpty());
		Assert.assertFalse(academia.getCnpj().isEmpty());
		Assert.assertFalse(academia.getNomeFantasia().isEmpty());
		Assert.assertTrue(academia.getId().equals(1000L));
		
	}
	
	/**
	 * Teste do find by id com registro inexistente
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/academias.sql"
	})
	public void findAcademiaMustFailById(){

		this.academiaService
				.findAcdemiaById(100156160L);
		
	}
	
	//TODO fazer testes com filtros
	
}
