package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.anamnese;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.anamnese.Resposta;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.anamnese.IRespostaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class RespostaService {

	@Autowired
	private IRespostaRepository respostaRepository;
	
	/**
	 * 
	 * Realiza a inserção de uma resposta na base
	 * 
	 * @param resposta
	 * @return
	 */
	public Resposta insertResposta( Resposta resposta ){
		
		Assert.notNull(
			resposta,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			resposta.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.respostaRepository.save( resposta );
		
	}
	
	/**
	 * Realiza update de uma resposta da avaliação fisica
	 * @param resposta
	 * @return
	 */
	public Resposta updateResposta( Resposta resposta ){
		
		Assert.notNull(
			resposta,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			resposta.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.respostaRepository.save( resposta );
		
	}
	
	/**
	 * Busca uma determinada resposta por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Resposta findRespostaById( long id ){
		return this.respostaRepository
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
