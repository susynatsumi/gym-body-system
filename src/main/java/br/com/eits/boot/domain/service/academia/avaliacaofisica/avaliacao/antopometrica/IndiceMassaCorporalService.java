package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.IndiceMassaCorporal;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IIndiceMassaCorporalRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class IndiceMassaCorporalService {

	@Autowired
	private IIndiceMassaCorporalRepository indiceMassaCorporalRepository;
	
	/**
	 * 
	 * Realiza a inserção de um registro na base
	 * 
	 * @param resposta
	 * @return
	 */
	public IndiceMassaCorporal insertResposta( IndiceMassaCorporal indiceMassaCorporal ){
		
		Assert.notNull(
			indiceMassaCorporal,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			indiceMassaCorporal.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.indiceMassaCorporalRepository.save( indiceMassaCorporal );
		
	}
	
	/**
	 * Realiza update
	 * 
	 * @param resposta
	 * @return
	 */
	public IndiceMassaCorporal updateResposta( IndiceMassaCorporal dobrasCutaneas ){
		
		Assert.notNull(
			dobrasCutaneas,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			dobrasCutaneas.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.indiceMassaCorporalRepository.save( dobrasCutaneas );
		
	}
	
	/**
	 * Busca registro por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public IndiceMassaCorporal findRespostaById( long id ){
		return this.indiceMassaCorporalRepository
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
