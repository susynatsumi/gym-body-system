package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao.antopometrica;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.antopometrica.AvaliacaoAntropometrica;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.antopometrica.IAvaliacaoAntropometricaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class AvaliacaoAntropometricaService {

	@Autowired
	private IAvaliacaoAntropometricaRepository antropometricaRepository;
	
	/**
	 * Realiza inserção de uma avaliação antropometrica 
	 * @param avaliacaoAntropometrica
	 * @return
	 */
	public AvaliacaoAntropometrica insertAvaliacaoAntropometrica( AvaliacaoAntropometrica avaliacaoAntropometrica ){
		
		Assert.notNull(
			avaliacaoAntropometrica,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			avaliacaoAntropometrica.getId(), 
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.antropometricaRepository.save( avaliacaoAntropometrica );
	}
	
	/**
	 * 
	 * Realiza update de uma avaliacao antropometrica 
	 * 
	 * @param avaliacaoAntropometrica
	 * @return
	 */
	public AvaliacaoAntropometrica updateAvaliacaoAntropometrica( 
		AvaliacaoAntropometrica avaliacaoAntropometrica 
	){
		
		Assert.notNull(
			avaliacaoAntropometrica,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			avaliacaoAntropometrica.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		return this.antropometricaRepository.save( avaliacaoAntropometrica );
	}
	
	/**
	 * Busca uma avaliacao Antropométrica por id 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public AvaliacaoAntropometrica findAvaliacaoAntropometricaById( Long id ){
		return this.antropometricaRepository
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
