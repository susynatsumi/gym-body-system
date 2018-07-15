package br.com.eits.boot.domain.service.academia.exercicio;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ExercicioService {

	// -------------------------------------------------
	// ------------ ATRIBUTOS --------------------------
	// -------------------------------------------------

	@Autowired
	private IExercicioRepository exercicioRepository;
	
	// -------------------------------------------------
	// -------------MÉTODOS ----------------------------
	// -------------------------------------------------
	
	/**
	 * Insere um novo exercício 
	 * 
	 * @param exercicio
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Exercicio insertExercicio( Exercicio exercicio ) {
		
		Assert.notNull(
			exercicio,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			exercicio.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		exercicio.setIsAtivo(true);
		
		return this.exercicioRepository.save( exercicio );
		
	}
	
	/**
	 * Faz update no exercicio
	 * 
	 * @param exercicio
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Exercicio updateExercicio( Exercicio exercicio ){

		Assert.notNull(
			exercicio,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			exercicio.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.exercicioRepository.save( exercicio );
		
	}
	
	/**
	 * Busca um determinado exercicio por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Exercicio findExercicioById( long id ){
		
		return this.exercicioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
					MessageSourceHolder.translate(
						"repository.notFoundById", id
					)
				)
			);
	}
	
	/**
	 * Lista exercicios de acordo com os filtros 
	 * 
	 * @param filtros
	 * @param isAtivo
	 * @param pageable
	 * @return
	 * 
	 */
	@Transactional( readOnly = true )
	public Page<Exercicio> listExercicioByFilters(
		String filtros,
		Boolean isAtivo,
		Pageable pageable
	){
		return this.exercicioRepository.listByFilters(filtros, isAtivo, pageable);
	}
	
}
