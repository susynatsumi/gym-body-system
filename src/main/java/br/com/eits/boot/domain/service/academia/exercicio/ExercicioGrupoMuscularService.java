package br.com.eits.boot.domain.service.academia.exercicio;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.exercicio.ExercicioGrupoMuscular;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioGrupoMuscularRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ExercicioGrupoMuscularService {

	// ------------------------------------------
	// ------------------ ATRIBUTOS -------------
	// ------------------------------------------
	
	@Autowired
	private IExercicioGrupoMuscularRepository exercicioGrupoMuscularRepository;
	
	// ------------------------------------------
	// -------------- MÉTODOS -------------------
	// ------------------------------------------
	
	/**
	 * 
	 * Realiza inserção de um exercicio grupo muscular 
	 * 
	 * @param exercicioGrupoMuscular
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public ExercicioGrupoMuscular insertExercicioGrupoMuscular( 
		ExercicioGrupoMuscular exercicioGrupoMuscular
	) {

		Assert.notNull( 
			exercicioGrupoMuscular, 
			MessageSourceHolder.translate("service.object.null")
		);
			
		Assert.isNull(
			exercicioGrupoMuscular.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);

		return this.exercicioGrupoMuscularRepository.save( exercicioGrupoMuscular );
		
	}
	
	/**
	 * Realiza update de um exercicio grupo muscular
	 *  
	 * @param exercicioGrupoMuscular
	 * @return
	 */
	public ExercicioGrupoMuscular updateExercicioGrupoMuscular(
		ExercicioGrupoMuscular exercicioGrupoMuscular
	) {
		Assert.notNull( 
			exercicioGrupoMuscular, 
			MessageSourceHolder.translate("service.object.null")
		);
			
		Assert.notNull(
			exercicioGrupoMuscular.getId(),
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		return this.exercicioGrupoMuscularRepository.save(exercicioGrupoMuscular);
	}
	
	/**
	 * Realiza busca de um exercicio grupo muscular por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public ExercicioGrupoMuscular findExercicioGrupoMuscularById(
		long id
	) {
		
		return this.exercicioGrupoMuscularRepository
				.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
					MessageSourceHolder.translate("repository.notFoundById", id)
				)
		);
		
	}
	
	/**
	 * 
	 * Remove da base um exercicio grupo muscular de acordo com seu id
	 * 
	 * @param id
	 */
	public void deleteById( long id ){
		this.exercicioGrupoMuscularRepository.deleteById(id);
	}
	// verificar listagem por filtros 
}
