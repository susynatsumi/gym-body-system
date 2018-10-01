package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.IAvaliacaoFisicaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class AvaliacaoFisicaService {

	@Autowired
	private IAvaliacaoFisicaRepository iAvaliacaoFisicaRepository;
	
	/**
	 * 
	 * Realiza a inserção de um registro na base
	 * 
	 * @param avaliacaoFisica
	 * @return
	 */
	public AvaliacaoFisica insertAvaliacaoFisica( AvaliacaoFisica avaliacaoFisica ){
		
		Assert.notNull(
			avaliacaoFisica,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			avaliacaoFisica.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		Assert.isNull(
			avaliacaoFisica.getAvaliacaoAntropometrica(),
			MessageSourceHolder.translate("service.object.null")
		);
		
		avaliacaoFisica.getAvaliacaoAntropometrica().setAvaliacaoFisica(avaliacaoFisica);
		
		return this.iAvaliacaoFisicaRepository.save( avaliacaoFisica );
		
	}
	
	/**
	 * Realiza update
	 * 
	 * @param avaliacaoFisica
	 * @return
	 */
	public AvaliacaoFisica updateAvaliacaoFisica( AvaliacaoFisica avaliacaoFisica ){
		
		Assert.notNull(
			avaliacaoFisica,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			avaliacaoFisica.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.iAvaliacaoFisicaRepository.save( avaliacaoFisica );
		
	}
	
	/**
	 * Busca registro por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public AvaliacaoFisica findAvaliacaoFisicaById( long id ){
		return this.iAvaliacaoFisicaRepository
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
