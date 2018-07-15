package br.com.eits.boot.domain.service.academia.treino;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioTreinoData;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.treino.IExercicioTreinoDataRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ExercicioTreinoDataService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IExercicioTreinoDataRepository exercicioTreinoDataRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere um exercicio treino data na base de dados
	 * 
	 * @param exercicioTreinoData
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public ExercicioTreinoData insertExercicioTreinoData(
		ExercicioTreinoData exercicioTreinoData
	){
		
		Assert.notNull(
			exercicioTreinoData,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			exercicioTreinoData.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.exercicioTreinoDataRepository.save(exercicioTreinoData);
	}
	
	/**
	 * Realiza update de um exercicio treino data 
	 * 
	 * @param exercicioTreinoData
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public ExercicioTreinoData updateExercicioTreinoData(
		ExercicioTreinoData exercicioTreinoData 
	){
		
		Assert.notNull(
			exercicioTreinoData,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			exercicioTreinoData.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.exercicioTreinoDataRepository.save(exercicioTreinoData);
	}
	
	/**
	 * 
	 * Busca exercicio treino data por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public ExercicioTreinoData findExercicioTreinoDataById( Long id ){
		
		return this.exercicioTreinoDataRepository
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
