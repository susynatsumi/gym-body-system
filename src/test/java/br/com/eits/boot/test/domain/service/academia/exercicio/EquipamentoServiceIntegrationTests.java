package br.com.eits.boot.test.domain.service.academia.exercicio;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.exercicio.Equipamento;
import br.com.eits.boot.domain.repository.academia.exercicio.IEquipamentoRepository;
import br.com.eits.boot.domain.service.academia.exercicio.EquipamentoService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class EquipamentoServiceIntegrationTests extends AbstractIntegrationTests{

	@Autowired
	private EquipamentoService equipamentoService;
	
	@Autowired
	private IEquipamentoRepository equipamentoRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Teste de inserção de equipamento com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/equipamentos.sql"
	})
	public void insertEquipamentoMustPass(){
		
		final Equipamento equipamento = new Equipamento("Esteira", null, false);
		
		final Equipamento inserido = this.equipamentoService
				.insertEquipamento(equipamento);
		
		Assert.assertNotNull(inserido);
		Assert.assertNotNull(inserido.getId());
		Assert.assertTrue(inserido.getIsAtivo());
	}

	/**
	 *
	 * Teste de inserção de equipamento sem informar dados do campo 
	 * obrigatório descricao
	 * 
	 * 
	 */
	@Test(expected = ValidationException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/equipamentos.sql"
	})
	public void insertEquipamentoMustFailMandatoryFieldDescricao(){

		final Equipamento equipamento = new Equipamento("", null, false);
		
		this.equipamentoService.insertEquipamento(equipamento);
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Teste de update com sucesso do campo descrição do equipamento
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/equipamentos.sql"
	})
	public void updateEquipamentoMustPassMandatoryFieldDescricao(){
		
		Equipamento equipamento = this.equipamentoRepository
				.findById(1000L)
				.orElse(null);

		equipamento.setDescricao("xxxxxxxxxxxxxxxxxxxxxxxxxxx");
		
		this.equipamentoService.updateEquipamento(equipamento);
		
		equipamento = this.equipamentoRepository
				.findById(1000L)
				.orElse(null);
		
		Assert.assertNotNull(equipamento);
		Assert.assertNotNull(equipamento.getId());
		Assert.assertEquals("xxxxxxxxxxxxxxxxxxxxxxxxxxx", equipamento.getDescricao());
		
	}
	
	/**
	 * Teste de update setando um valor 
	 */
	@Test( expected = TransactionSystemException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/equipamentos.sql"
	})
	public void updateEquipamentoMustFailMandatoryFieldDescricao(){
		
		Equipamento equipamento = this.equipamentoRepository
				.findById(1000L)
				.orElse(null);

		equipamento.setDescricao("");
		
		this.equipamentoService.updateEquipamento(equipamento);
		
	}
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Teste do método para realizar buscas por id
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/equipamentos.sql"
	})
	public void findEquipamentoMustPass(){
		
		Equipamento equipamento = this.equipamentoService
				.findEquipamentoById(1000L);
		
		Assert.assertNotNull(equipamento);
		Assert.assertNotNull(equipamento.getId());
		
	}
	
	/**
	 * Teste do find com id inexistente
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/exercicio/equipamentos.sql"
	})
	public void findEquipamentoMustFail(){
		
		this.equipamentoService.findEquipamentoById(10411600L);
		
	}

	//TODO fazer testes com filtros
	
}
