package br.com.eits.boot.domain.service.academia.notificacao;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.notificacao.PessoaNotificacao;
import br.com.eits.boot.domain.repository.academia.notificacao.IPessoaNotificacaoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class PessoaNotificacaoService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IPessoaNotificacaoRepository pessoaNotificacaoRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere uma pessoa notificacao na base de dados
	 * @param pessoaNotificacao
	 * @return
	 */
	public PessoaNotificacao insertPessoaNotificacao( 
		PessoaNotificacao pessoaNotificacao 
	){
		Assert.notNull(
			pessoaNotificacao,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			pessoaNotificacao.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.pessoaNotificacaoRepository.save(pessoaNotificacao);
	}
	
	/**
	 * Realiza update de uma pessoa notificação 
	 * 
	 * @param pessoaNotificacao
	 * @return
	 */
	public PessoaNotificacao updatePessoaNotificacao( 
		PessoaNotificacao pessoaNotificacao 
	){
		
		Assert.notNull(
			pessoaNotificacao,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			pessoaNotificacao.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.pessoaNotificacaoRepository.save(pessoaNotificacao);
	}
	
	/**
	 * 
	 * Busca pessoa notificação por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public PessoaNotificacao findPessoaNotificacaoById( Long id ){
		return this.pessoaNotificacaoRepository
				.findById(id)
				.orElseThrow(() ->
					new IllegalArgumentException(
						MessageSourceHolder.translate(
							"repository.notFoundById", id
						)
					)
				);
	}
	
}
