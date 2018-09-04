package br.com.eits.boot.domain.service.academia.treino;

import java.util.List;
import java.util.stream.Collectors;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioRealizado;
import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
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
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
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
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
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
	
	/**
	 * Cria um exercicio realizado para todos os exercicio do treino
	 * @param treinoData
	 * @return
	 */
	public List<ExercicioRealizado> criaExercicioRealizado( TreinoData treinoData ){
		
		Assert.notNull(
			treinoData, 
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			treinoData.getTreino(), 
			MessageSourceHolder.translate("service.object.null")
		);
		
		final Treino treino = treinoData.getTreino();
		
		Assert.notNull(
			treinoData.getTreino().getTreinoExercicios(), 
			MessageSourceHolder.translate("service.object.null")
		);
		
		//cria para cada exercicio do treino um exercicio realizado
		return treino.getTreinoExercicios().stream()
			.map(treinoExercicio -> 
				new ExercicioRealizado(false, treinoData, treinoExercicio
			)).collect(Collectors.toList());
		
	}
	
}
