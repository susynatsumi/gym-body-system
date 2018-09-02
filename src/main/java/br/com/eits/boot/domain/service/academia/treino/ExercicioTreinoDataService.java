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
	 * @param exercicioRealizado
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public ExercicioRealizado insertExercicioTreinoData(
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
		
		return this.exercicioTreinoDataRepository.save(exercicioRealizado);
	}
	
	/**
	 * Realiza update de um exercicio treino data 
	 * 
	 * @param exercicioRealizado
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public ExercicioRealizado updateExercicioTreinoData(
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
		
		return this.exercicioTreinoDataRepository.save(exercicioRealizado);
	}
	
	/**
	 * 
	 * Busca exercicio treino data por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public ExercicioRealizado findExercicioTreinoDataById( Long id ){
		
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
	
	/**
	 * Cria um exercicio treino data para todos os exercicio do treino
	 * @param treinoData
	 * @return
	 */
	public List<ExercicioRealizado> criaExercicioTreinoData( TreinoData treinoData ){
		
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
		
		//cria para cada exercicio do treino um exercicio treino data
		return treino.getTreinoExercicios().stream()
			.map(treinoExercicio -> 
				new ExercicioRealizado(false, treinoData, treinoExercicio
			)).collect(Collectors.toList());
		
	}
	
}
