package br.com.eits.boot.test.domain.service.academia.avaliacaofisica.avaliacao;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionSystemException;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.Perimetria;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.anamnese.Resposta;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.TipoProtocolo;
import br.com.eits.boot.domain.entity.academia.pessoa.Genero;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.IAvaliacaoFisicaRepository;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.AvaliacaoFisicaService;
import br.com.eits.boot.test.domain.AbstractIntegrationTests;

public class AvaliacaoFisicaServiceIntegrationTests extends AbstractIntegrationTests {

	// ------------------------------------------------------
	// ------------------- ATRIBUTOS ------------------------
	// ------------------------------------------------------
	
	@Autowired
	private IAvaliacaoFisicaRepository avaliacaoFisicaRepository;
	
	@Autowired
	private AvaliacaoFisicaService avaliacaoFisicaService;
	
	@Autowired
	private IPessoaRepository pessoaRepository;
	
	// ------------------------------------------------------
	// --------- INSERTS ------------------------------------
	// ------------------------------------------------------
	
	/**
	 * Busca uma pessoa para avaliacao fisica
	 * @return
	 */
	private Pessoa findAluno(){
		
		return this.pessoaRepository.findById(1014L).orElse(null);
		
	}

	/**
	 * Faz mock de uma perimetria qualquer para teste
	 * @return
	 */
	private Perimetria mockPerimetria(){
		
		return new Perimetria(
				null, 
				10d, 
				50d, 
				5d, 
				5d, 
				8d, 
				8d, 
				6d, 
				6d, 
				100d, 
				100d, 
				100d, 
				80d, 
				80d, 
				70d, 
				70d, 
				60d, 
				60d, 
				50d, 
				50d 
			);
		
	}
	
	/**
	 * Faz mock de uma resposta qualquer para teste
	 * @return
	 */
	private Resposta mockResposta(){
		
		return 
			new Resposta(
				null, 
				"objetivosAtividadeFisica", 
				"praticaAtividade", 
				"medicamento", 
				"cirurgia", 
				"doencaFamiliar", 
				"observacao" 
			);
		
	}
	
	
	private AvaliacaoAntropometrica mockAvalicaoAntropometrica(){
		
		DobrasCutaneas dobrasCutaneas = new DobrasCutaneas(
			null, 
			10d, 
			11d, 
			16d, 
			15d, 
			156d, 
			11d, 
			156d, 
			165d, 
			100d, 
			165d
		);
			
		IndiceMassaCorporal indiceMassaCorporal = new IndiceMassaCorporal(
			null, 
			BigDecimal.valueOf(1.90d), 
			BigDecimal.valueOf(100),
			BigDecimal.valueOf(20)
		);
		
		PredicaoGorduraSiri predicaoGorduraSiri = new PredicaoGorduraSiri(
			null, 
			10d, 
			16d
		);
		
		
		return new AvaliacaoAntropometrica(
			null, 
			dobrasCutaneas, indiceMassaCorporal, predicaoGorduraSiri, 20d, TipoProtocolo.POLLOCK);
	}
	
	/**
	 * Faz mock de uma avaliacao fisica para calculo da densidade corporal feminina
	 * @return
	 */
	private AvaliacaoFisica mockAvaliacaoFisicaFeminino(){
		
		final Pessoa pessoa = new Pessoa();
		pessoa.setGenero(Genero.FEMININO);
//		final int ano = LocalDate.now().getYear() - 51;
		pessoa.setDataNascimento(LocalDate.of(1967, 9, 20));
		
		final DobrasCutaneas dobrasCutaneas = new DobrasCutaneas(
			0L, 
			15d//tricipal
			,0d// bicital
			,21d //subescapular
			, 9d // peitoral
			,9d// toraxica
			,15d// axilarMedia
			,15d// supraIliaca
			,19d// abdominal
			,25d// coxa
			, null //panturrilha
		);
		
		final AvaliacaoAntropometrica protocoloPollock = new AvaliacaoAntropometrica(
			null, 
			dobrasCutaneas, 
			null,
			null,
			null,
			TipoProtocolo.POLLOCK
		);
		
		final AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			LocalDate.now(), 
			pessoa, 
			null, 
			null, 
			protocoloPollock
		);
		
		protocoloPollock.setAvaliacaoFisica(avaliacaoFisica);
		
		return avaliacaoFisica;
		
	}
	
	// ----------------------------------------------------------------
	// ----------------------- TESTES INSERT --------------------------
	// ----------------------------------------------------------------
	
	
	/**s
	 * Insere uma nova avaliacao Física
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustPass(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			LocalDate.now(), 
			findAluno(), 
			mockPerimetria(), 
			mockResposta(), 
			mockAvalicaoAntropometrica()
		);
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
		Assert.assertNotNull(avaliacaoFisica);
		Assert.assertNotNull(avaliacaoFisica.getId());
		Assert.assertNotNull(avaliacaoFisica.getData());
		
		Assert.assertNotNull(avaliacaoFisica.getPerimetria());
		Assert.assertNotNull(avaliacaoFisica.getPerimetria().getId());
		
		Assert.assertNotNull(avaliacaoFisica.getResposta());
		Assert.assertNotNull(avaliacaoFisica.getResposta().getId());
		
	}
	
	/**
	 * Falha na inserção de uma avaliacao fisica sem os atributos obrigatorios
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustPassFail(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica();
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	/**
	 * Falha na inserção de uma avaliacao fisica sem a data
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustFailMandatoryFieldData(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			null, // data 
			findAluno(), 
			mockPerimetria(), 
			mockResposta(), 
			mockAvalicaoAntropometrica()
		);
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	/**
	 * Falha na inserção de uma avaliacao fisica sem a pessoa
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustFailMandatoryFieldPessoaId(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			LocalDate.now(), // data 
			null, 
			mockPerimetria(), 
			mockResposta(), 
			mockAvalicaoAntropometrica()
		);
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	/**
	 * Falha na inserção de uma avaliacao fisica avaliacao antropometrica
	 */
	@Test( expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustFailMandatoryFieldAvaliacaoAntropometricaId(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			LocalDate.now(), // data 
			findAluno(), 
			mockPerimetria(), 
			mockResposta(),
			null
		);
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	/**
	 * Falha na inserção de uma avaliacao fisica sem a perimetria
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustFailMandatoryFieldPerimetriaId(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			LocalDate.now(), // data 
			findAluno(), 
			null, 
			mockResposta(), 
			mockAvalicaoAntropometrica()
		);
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	/**
	 * Falha na inserção de uma avaliacao fisica sem as respostas da anamnese
	 */
	@Test( expected = ValidationException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void insertAvaliacaoFisicaMustFailMandatoryFieldRespostaId(){
		
		AvaliacaoFisica avaliacaoFisica = new AvaliacaoFisica(
			null, 
			LocalDate.now(), // data 
			findAluno(), 
			mockPerimetria(), 
			null, 
			mockAvalicaoAntropometrica()
		);
		
		avaliacaoFisica = this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	// ------------------------------------------------------
	// --------- UPDATES ------------------------------------
	// ------------------------------------------------------

	/**
	 * Realiza update de uma avaliacao fisica com sucesso
	 */
	@Test()
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void updateAvaliacaoFisicaMustPass(){
		
		AvaliacaoFisica avaliacaoFisica = this.avaliacaoFisicaRepository
				.findById(1001L)
				.orElse(null);
		
		Assert.assertNotNull(avaliacaoFisica);
		
		LocalDate dataAnterior = avaliacaoFisica.getData();
		
		avaliacaoFisica.setData(LocalDate.of(1999, 1, 25));
		
		this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
		avaliacaoFisica = this.avaliacaoFisicaRepository
				.findById(1001L)
				.orElse(null);
		
		Assert.assertNotNull(avaliacaoFisica);
		Assert.assertEquals(dataAnterior, avaliacaoFisica.getData());
		
	}
	
	
	/**
	 * Valida obrigatoriedade da perimetria na avaliacao fisica
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void updateAvaliacaoFisicaMustFailMandatoryFieldPerimetriaId(){
		
		AvaliacaoFisica avaliacaoFisica = this.avaliacaoFisicaRepository
				.findById(1001L)
				.orElse(null);
		
		Assert.assertNotNull(avaliacaoFisica);
		avaliacaoFisica.setPerimetria(null);
		
		this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	/**
	 * Valida obrigatoriedade da resposta na avaliacao fisica 
	 */
	@Test( expected = TransactionSystemException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void updateAvaliacaoFisicaMustFailMandatoryFieldRespostaId(){
		
		AvaliacaoFisica avaliacaoFisica = this.avaliacaoFisicaRepository
				.findById(1001L)
				.orElse(null);
		
		Assert.assertNotNull(avaliacaoFisica);
		avaliacaoFisica.setResposta(null);
		
		this.avaliacaoFisicaService.salvarAvaliacaoFisica(avaliacaoFisica);
		
	}
	
	
	// ------------------------------------------------------
	// --------- FINDS ------------------------------------
	// ------------------------------------------------------

	/**
	 * Teste de busca por id com sucesso
	 */
	@Test
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void findAvaliacaoFisicaMustPassById(){
		
		AvaliacaoFisica avaliacaoFisica = this.avaliacaoFisicaService
				.findAvaliacaoFisicaById(1001L);
		
		Assert.assertNotNull(avaliacaoFisica);
		Assert.assertNotNull(avaliacaoFisica.getId());
		
	}
	
	/**
	 * Teste de busca de uma avaliacao fisica  por id inexistente
	 */
	@Test(expected = IllegalArgumentException.class )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void findAvaliacaoFisciaMustFailById(){
		
		this.avaliacaoFisicaService
			.findAvaliacaoFisicaById(151615615L);
		
	}
	
	
	
	/**
	 * teste de listagem de avaliacoes fisica por pessoa
	 */
	@Test( )
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void listAvaliacaoFisciaByPessoaIdMustPassReturn2(){
		
		
		Page<AvaliacaoFisica> avaliacoes = this.avaliacaoFisicaService.listAvalicaoFisicaByPessoaId(1011L, null);
		
		Assert.assertNotNull(avaliacoes);

		Assert.assertEquals(1L, avaliacoes.getTotalElements());
	}
	
	/**
	 * teste de listagem de avaliacoes fisica por pessoa, sem usuário logado
	 */
	@Test( expected = AuthenticationCredentialsNotFoundException.class )
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void listAvaliacaoFisciaByPessoaIdMustFailPessoaNaoLogada(){
		
		
		Page<AvaliacaoFisica> avaliacoes = this.avaliacaoFisicaService.listAvalicaoFisicaByPessoaId(1011L, null);
		
		Assert.assertNotNull(avaliacoes);
		
		Assert.assertEquals(avaliacoes.getTotalElements(), 2L);
	}
	
	
	/**
	 * teste de listagem de avaliacoes fisica por id, com valor inválido
	 */
	@Test( expected = IllegalArgumentException.class)
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
		"/dataset/academia/avaliacaoFisica/avaliacaoFisica.sql"
	})
	public void listAvaliacaoFisciaByPessoaIdMustFailIdInvalido(){
		
		this.avaliacaoFisicaService.listAvalicaoFisicaByPessoaId(null, null);
		
	}
	
	
	// ----------------------------------------------------
	// ----------------- calculo da avaliação física ------
	// ----------------------------------------------------
	
	@Test()
	@WithUserDetails("admin@email.com")
	@Sql({
		"/dataset/pessoa/pessoas.sql",
	})
	public void calculaAvaliacaoMulherMustPass(){

		final AvaliacaoFisica avaliacaoFisica = mockAvaliacaoFisicaFeminino();
		
		final AvaliacaoAntropometrica antropometrica = avaliacaoFisica.getAvaliacaoAntropometrica();
		
		antropometrica.calculaDensidadeCorporal();
		
		System.out.println(antropometrica.getDensidadeCorporal());
		
	}
	
	
}

