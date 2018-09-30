package br.com.eits.boot.domain.service.academia.treino;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class TreinoService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private ITreinoRepository treinoRepository;
	
	@Autowired
	private TreinoDataService treinoDataService;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere um treino na base de dados
	 * 
	 * @param treino
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Treino insertTreino(
		Treino treino
	){
		
		Assert.notNull(
			treino,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			treino.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		Assert.notEmpty(
			treino.getTreinoExercicios(),
			MessageSourceHolder.translate("service.treino.insert.exercicios.empty")
		);
		
		Assert.notEmpty(
			treino.getDiasSemanaSelecionados(),
			MessageSourceHolder.translate("service.treino.insert.dias.semana.empty")
		);
		
		Assert.notNull(
			treino.getDataInicio(),
			MessageSourceHolder.translate("service.treino.insert.data.inicio.null")
		);
		
		Assert.notNull(
			treino.getDataFim(),
			MessageSourceHolder.translate("service.treino.insert.data.fim.null")
		);
		
		Assert.isTrue(
			treino.getDataInicio().isBefore(treino.getDataFim()), 
			MessageSourceHolder.translate("service.treino.insert.data.fim.menor.data.inicio")
		);
		
		treino.getTreinoExercicios()
		.forEach(treinoExercico ->{
			treinoExercico.setTreino(treino);
		});
		
		
		Treino treinoInsert = this.treinoRepository.save(treino);
		
		treinoDataService.criaDatasTreino(treinoInsert);
		treino.setDiasSemanaSelecionados( null );
		
		return treino;
	}
	
	
	/**
	 * Realiza update de um treino 
	 * 
	 * @param treino
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Treino updateTreino(
		Treino treino 
	){
		
		Assert.notNull(
			treino,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			treino.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		Assert.notEmpty(
			treino.getTreinoExercicios(),
			MessageSourceHolder.translate("service.treino.insert.exercicios.empty")
		);
		
		treino.getTreinoExercicios()
		.forEach(treinoExercico ->{
			treinoExercico.setTreino(treino);
		});
		
		return this.treinoRepository.save(treino);
	}
	
	/**
	 * 
	 * Busca treino por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Treino findTreinoById( Long id ){
		
		final Treino treino = this.treinoRepository
				.findById(id)
				.orElseThrow(() ->
					new IllegalArgumentException(
						MessageSourceHolder.translate(
							"repository.notFoundById", id
						)
					)
				);
		
		return treino;
		
	}
	
	/**
	 * 
	 * Lista treinos de acordo com os filtros, nome do treino e nome do aluno e id do treino 
	 * 
	 * @param filters
	 * @param pageRequest
	 * @return
	 */
	@Transactional( readOnly = true )
	public Page<Treino> listTreinosByFilters(String filters, PageRequest pageRequest){
		Page<Treino> treinos = this.treinoRepository.listTreinosByFilters(filters, pageRequest);
		return treinos;
	}
	
	/**
	 * Remove um treino por id
	 * @param id
	 */
	public void deleteTreino( long id ){
		this.treinoRepository.deleteById(id);
	}
	
}
