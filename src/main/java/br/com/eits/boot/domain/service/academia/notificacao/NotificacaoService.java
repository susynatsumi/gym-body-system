package br.com.eits.boot.domain.service.academia.notificacao;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.notificacao.Notificacao;
import br.com.eits.boot.domain.repository.academia.notificacao.INotificacaoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class NotificacaoService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private INotificacaoRepository notificacaoRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere uma notificacao na base de dados
	 * @param notificacao
	 * @return
	 */
	public Notificacao insertNotificacao( Notificacao notificacao ){
		Assert.notNull(
			notificacao,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			notificacao.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.notificacaoRepository.save(notificacao);
	}
	
	/**
	 * Realiza update de uma notificação 
	 * 
	 * @param notificacao
	 * @return
	 */
	public Notificacao updateNotificacao( Notificacao notificacao ){
		
		Assert.notNull(
			notificacao,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			notificacao.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.notificacaoRepository.save(notificacao);
	}
	
	/**
	 * 
	 * Busca notificação por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Notificacao findNotificacaoById( Long id ){
		return this.notificacaoRepository
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
