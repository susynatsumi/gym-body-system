package br.com.eits.boot.domain.service.academia.notificacao;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.notificacao.DestinatarioNotificacao;
import br.com.eits.boot.domain.repository.academia.notificacao.IDestinatarioNotificacaoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class DestinatarioNotificacaoService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IDestinatarioNotificacaoRepository destinatarioNotificacaoRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere uma pessoa notificacao na base de dados
	 * @param destinatarioNotificacao
	 * @return
	 */
	public DestinatarioNotificacao insertPessoaNotificacao( 
		DestinatarioNotificacao destinatarioNotificacao 
	){
		Assert.notNull(
			destinatarioNotificacao,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			destinatarioNotificacao.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.destinatarioNotificacaoRepository.save(destinatarioNotificacao);
	}
	
	/**
	 * Realiza update de uma pessoa notificação 
	 * 
	 * @param destinatarioNotificacao
	 * @return
	 */
	public DestinatarioNotificacao updatePessoaNotificacao( 
		DestinatarioNotificacao destinatarioNotificacao 
	){
		
		Assert.notNull(
			destinatarioNotificacao,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			destinatarioNotificacao.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.destinatarioNotificacaoRepository.save(destinatarioNotificacao);
	}
	
	/**
	 * 
	 * Busca pessoa notificação por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public DestinatarioNotificacao findPessoaNotificacaoById( Long id ){
		return this.destinatarioNotificacaoRepository
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
