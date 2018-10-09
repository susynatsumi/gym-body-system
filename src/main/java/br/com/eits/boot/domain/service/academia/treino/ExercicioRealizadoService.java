package br.com.eits.boot.domain.service.academia.treino;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioRealizado;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.treino.IExercicioRealizadoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ExercicioRealizadoService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IExercicioRealizadoRepository exercicioRealizadoRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere um exercicio realizado na base de dados
	 * 
	 * @param exercicioRealizado
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public ExercicioRealizado insertExercicioRealizado(
		ExercicioRealizado exercicioRealizado
	){
		
		Assert.notNull(
			exercicioRealizado,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			exercicioRealizado.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		return this.exercicioRealizadoRepository.save(exercicioRealizado);
	}
	
	/**
	 * Realiza update de um exercicio realizado 
	 * 
	 * @param exercicioRealizado
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public ExercicioRealizado updateExercicioRealizado(
		ExercicioRealizado exercicioRealizado 
	){
		
		Assert.notNull(
			exercicioRealizado,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			exercicioRealizado.getId(),
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		return this.exercicioRealizadoRepository.save(exercicioRealizado);
	}
	
	/**
	 * 
	 * Busca exercicio realizado por id
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public ExercicioRealizado findExercicioRealizadoById( Long id ){
		
		return this.exercicioRealizadoRepository
				.findById(id)
				.orElseThrow(() ->
					new IllegalArgumentException(
						MessageSourceHolder.translate(
							"repository.notFoundById", id
						)
					)
				);
		
	}
	
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<ExercicioRealizado> findExercicioRealizadoByTreinoDataId(
		Long idTreinoData,
		PageRequest pageRequest
	) {
		
		return this.exercicioRealizadoRepository.findByTreinoData_id(idTreinoData, pageRequest);
		
	}
	
	
	
	
}
