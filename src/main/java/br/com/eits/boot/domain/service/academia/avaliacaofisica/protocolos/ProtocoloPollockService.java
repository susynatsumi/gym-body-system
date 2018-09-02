package br.com.eits.boot.domain.service.academia.avaliacaofisica.protocolos;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.ProtocoloPollock;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.protocolos.IProtocoloPollockRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ProtocoloPollockService {

	@Autowired
	private IProtocoloPollockRepository pollockRepository;
	
	/**
	 * Realiza inserção de um protocolo de pollock
	 * 
	 * @param protocoloPollock
	 * @return
	 */
	public ProtocoloPollock insertProtocoloPollock( ProtocoloPollock protocoloPollock ){
		
		Assert.notNull(
			protocoloPollock,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			protocoloPollock.getId(), 
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.pollockRepository.save( protocoloPollock );
	}
	
	/**
	 * 
	 * Realiza update de um protocolo de pollock 
	 * 
	 * @param protocoloPollock
	 * @return
	 */
	public ProtocoloPollock updateProtocoloPollock( ProtocoloPollock protocoloPollock ){
		
		Assert.notNull(
			protocoloPollock,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			protocoloPollock.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		return this.pollockRepository.save(protocoloPollock);
	}
	
	/**
	 * Busca um protocolo de pollock por id 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public ProtocoloPollock findProtocoloPollockById( Long id ){
		return this.pollockRepository
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
