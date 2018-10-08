package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao;

import java.time.LocalDate;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.IAvaliacaoFisicaRepository;
import br.com.eits.boot.domain.service.AccountService;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class AvaliacaoFisicaService {

	@Autowired
	private IAvaliacaoFisicaRepository iAvaliacaoFisicaRepository;
	
	private AccountService accountService;
	
	/**
	 * 
	 * Realiza a inserção de um registro na base
	 * 
	 * @param avaliacaoFisica
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public AvaliacaoFisica insertAvaliacaoFisica( AvaliacaoFisica avaliacaoFisica ){
		
		Assert.notNull(
			avaliacaoFisica,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			avaliacaoFisica.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		Assert.notNull(
			avaliacaoFisica.getAvaliacaoAntropometrica(),
			MessageSourceHolder.translate("service.object.null")
		);
		
		avaliacaoFisica.getAvaliacaoAntropometrica().setAvaliacaoFisica(avaliacaoFisica);

		avaliacaoFisica.getAvaliacaoAntropometrica().calculaDensidadeCorporal();
		
		return this.iAvaliacaoFisicaRepository.save( avaliacaoFisica );
		
	}
	
	/**
	 * Realiza update
	 * 
	 * @param avaliacaoFisica
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public AvaliacaoFisica updateAvaliacaoFisica( AvaliacaoFisica avaliacaoFisica ){
		
		Assert.notNull(
			avaliacaoFisica,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			avaliacaoFisica.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.iAvaliacaoFisicaRepository.save( avaliacaoFisica );
		
	}
	
	/**
	 * Busca registro por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public AvaliacaoFisica findAvaliacaoFisicaById( long id ){
		return this.iAvaliacaoFisicaRepository
				.findById(id)
				.orElseThrow(() ->
					new IllegalArgumentException(
						MessageSourceHolder.translate(
							"repository.notFoundById", id
						)
					)
				);
	}
	
	/**
	 * Lista avaliações fisicas pelos fitros, nome treino, id pessoa, nome pessoa
	 * Alunos não podem acessar este método, pois não podem ver avaliacoes de outras pessoas, somente as suas
	 * @param filters
	 * @param pageRequest
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<AvaliacaoFisica> listAvaliacaoFisicaByFilters(
		String filters,
		Long idPessoa,
		LocalDate dataInicio, 
		LocalDate dataFim, 
		PageRequest pageRequest
	){
		
		return this.iAvaliacaoFisicaRepository.listAvaliacaoFisicaByFilters(filters, idPessoa, dataInicio, dataFim, pageRequest);
		
	}
	
	/**
	 *
	 * Lista a avaliacao fisica de uma pessoa, através de seu id
	 * 
	 * Usado principalmente por alunos
	 * 
	 * @param idPessoa
	 * @param pageRequest
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<AvaliacaoFisica> listAvalicaoFisicaByPessoaId(Long idPessoa, PageRequest pageRequest){
		
		Assert.notNull(idPessoa, MessageSourceHolder.translate("service.object.id.not.null"));
		
		return this.iAvaliacaoFisicaRepository.findAvaliacaoFisicaByPessoa_id(idPessoa, pageRequest);
		
	}
	
}
