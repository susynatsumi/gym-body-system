package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.PredicaoGorduraSiri;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IPredicaoGorduraSiriRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class PredicaoGorduraSiriService {

	@Autowired
	private IPredicaoGorduraSiriRepository predicaoGorduraSiriRepository;
	
	/**
	 * 
	 * Realiza a inserção de um registro na base
	 * 
	 * @param resposta
	 * @return
	 */
	public PredicaoGorduraSiri insertResposta( PredicaoGorduraSiri predicaoGorduraSiri ){
		
		Assert.notNull(
			predicaoGorduraSiri,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			predicaoGorduraSiri.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.predicaoGorduraSiriRepository.save( predicaoGorduraSiri );
		
	}
	
	/**
	 * Realiza update
	 * 
	 * @param resposta
	 * @return
	 */
	public PredicaoGorduraSiri updateResposta( PredicaoGorduraSiri predicaoGorduraSiri ){
		
		Assert.notNull(
			predicaoGorduraSiri,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			predicaoGorduraSiri.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.predicaoGorduraSiriRepository.save( predicaoGorduraSiri );
		
	}
	
	/**
	 * Busca registro por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public PredicaoGorduraSiri findRespostaById( long id ){
		return this.predicaoGorduraSiriRepository
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
