package br.com.eits.boot.domain.service.academia.avaliacaofisica.protocolos;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.protocolos.ProtocoloGuedes;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.protocolos.IProtocoloGuedesRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ProtocoloGuedesService {

	@Autowired
	private IProtocoloGuedesRepository guedesRepository;
	
	/**
	 * Realiza inserção de um protocolo de Guedes 
	 * @param protocoloGuedes
	 * @return
	 */
	public ProtocoloGuedes insertProtocoloGuedes( ProtocoloGuedes protocoloGuedes ){
		
		Assert.notNull(
				protocoloGuedes,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
				protocoloGuedes.getId(), 
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.guedesRepository.save( protocoloGuedes );
	}
	
	/**
	 * 
	 * Realiza update de um protocolo de Guedes 
	 * 
	 * @param protocoloGuedes
	 * @return
	 */
	public ProtocoloGuedes updateProtocoloGuedes( ProtocoloGuedes protocoloGuedes ){
		
		Assert.notNull(
			protocoloGuedes,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			protocoloGuedes.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		return this.guedesRepository.save( protocoloGuedes );
	}
	
	/**
	 * Busca um protocolo de Guedes por id 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public ProtocoloGuedes findProtocoloGuedesById( Long id ){
		return this.guedesRepository
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
