package br.com.eits.boot.domain.service.academia.treino;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoExercicioRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class TreinoExercicioService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private ITreinoExercicioRepository treinoExercicioRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere um treino exercicio na base de dados
	 * 
	 * @param treinoExercicio
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public TreinoExercicio insertTreinoExercicio(
		TreinoExercicio treinoExercicio
	){
		
		Assert.notNull(
			treinoExercicio,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			treinoExercicio.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.treinoExercicioRepository.save(treinoExercicio);
	}
	
	/**
	 * Realiza update de um treino exercicio
	 * 
	 * @param treinoExercicio
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public TreinoExercicio updateTreinoExercicio(
		TreinoExercicio treinoExercicio 
	){
		
		Assert.notNull(
			treinoExercicio,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			treinoExercicio.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.treinoExercicioRepository.save(treinoExercicio);
	}
	
	/**
	 * 
	 * Busca treino exercicio por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public TreinoExercicio findTreinoExercicioById( Long id ){
		
		return this.treinoExercicioRepository
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
