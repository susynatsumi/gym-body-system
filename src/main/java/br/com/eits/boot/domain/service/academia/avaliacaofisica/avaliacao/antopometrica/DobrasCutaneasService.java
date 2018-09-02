package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.DobrasCutaneas;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IDobrasCutaneasRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class DobrasCutaneasService {

	@Autowired
	private IDobrasCutaneasRepository dobrasCutaneasRepository;
	
	/**
	 * 
	 * Realiza a inserção de um registro na base
	 * 
	 * @param resposta
	 * @return
	 */
	public DobrasCutaneas insertResposta( DobrasCutaneas dobrasCutaneas ){
		
		Assert.notNull(
			dobrasCutaneas,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			dobrasCutaneas.getId(),
			MessageSourceHolder.translate("sercice.object.id.null")
		);
		
		return this.dobrasCutaneasRepository.save( dobrasCutaneas );
		
	}
	
	/**
	 * Realiza update
	 * 
	 * @param resposta
	 * @return
	 */
	public DobrasCutaneas updateResposta( DobrasCutaneas dobrasCutaneas ){
		
		Assert.notNull(
			dobrasCutaneas,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			dobrasCutaneas.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.dobrasCutaneasRepository.save( dobrasCutaneas );
		
	}
	
	/**
	 * Busca registro por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public DobrasCutaneas findRespostaById( long id ){
		return this.dobrasCutaneasRepository
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
