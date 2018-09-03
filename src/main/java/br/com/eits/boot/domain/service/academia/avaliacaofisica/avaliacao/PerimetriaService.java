package br.com.eits.boot.domain.service.academia.avaliacaofisica.avaliacao;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.Perimetria;
import br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao.IPerimetriaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class PerimetriaService {


	@Autowired
	private IPerimetriaRepository perimetriaRepository;
	
	/**
	 * 
	 * Realiza a inserção de um registro na base
	 * 
	 * @param perimetria
	 * @return
	 */
	public Perimetria insertPerimetria( Perimetria perimetria ){
		
		Assert.notNull(
			perimetria,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			perimetria.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.perimetriaRepository.save( perimetria );
		
	}
	
	/**
	 * Realiza update
	 * 
	 * @param perimetria
	 * @return
	 */
	public Perimetria updatePerimetria( Perimetria perimetria ){
		
		Assert.notNull(
			perimetria,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			perimetria.getId(), 
			MessageSourceHolder.translate("service.object.id.not.null")
		);
			
		
		return this.perimetriaRepository.save( perimetria );
		
	}
	
	/**
	 * Busca registro por id
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Perimetria findPerimetriaById( long id ){
		return this.perimetriaRepository
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
