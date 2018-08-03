package br.com.eits.boot.domain.service.academia.exercicio;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.academia.exercicio.ExercicioGrupoMuscular;
import br.com.eits.boot.domain.entity.academia.exercicio.GrupoMuscular;
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
	 * 
	 * Cria exercicio grupo musuclar de acordo com os parametros
	 * 
	 * @param gruposMusculares
	 * @param exercicio
	 * @return
	 */
	@Transactional( readOnly = true )
	public List<ExercicioGrupoMuscular> criaExercicioGrupoMuscular(
		Exercicio exercicio
	){
		List<ExercicioGrupoMuscular> novosExerciciosGruposMusculares = 
				new ArrayList<ExercicioGrupoMuscular>();
		
		exercicio.getGruposMusculares().forEach(grupo ->{
			novosExerciciosGruposMusculares.add(
				new ExercicioGrupoMuscular(exercicio, grupo)
			);
		});
			
		return novosExerciciosGruposMusculares;
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
	
	
	// verificar listagem por filtros 
}
