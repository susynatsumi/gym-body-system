package br.com.eits.boot.domain.service.academia.treino;

import java.time.LocalDate;
import java.util.ArrayList;

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
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public Treino insertTreino(
		Treino treino
	){
		System.out.println(treino.getDataInicio());
		System.out.println(treino.getDataFim());
		
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
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
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
		
		treino.setTreinoDatas(new ArrayList<>());
		
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
	 * 
	 * Lista os treinos que o aluno pode realizar nos proximos dias 
	 * 
	 * @param dataInicio
	 * @param dataTermino
	 * @param idAluno
	 * @param somenteCompletos
	 * @param pageRequest
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional(readOnly = true)
	public Page<Treino> listTreinosComDatasByFilters(
		LocalDate dataInicio ,
		LocalDate dataTermino ,
		Long idAluno,
		Boolean somenteCompletos,
		PageRequest pageRequest
	){
		
		Assert.notNull(
			dataInicio, 
			MessageSourceHolder.translate("service.treino.data.informe.data.inicio")
		);
		
		Assert.notNull(
				dataTermino, 
			MessageSourceHolder.translate("service.treino.data.informe.data.termino")
		);
		
		Assert.notNull(
			idAluno,
			MessageSourceHolder.translate("service.treino.data.informe.codigo.aluno")
		);
		
		return this.treinoRepository.listTreinosComDatasByFilters(
			idAluno, 
			dataInicio, 
			dataTermino, 
			somenteCompletos,
			pageRequest
		);
	}
	
	/**
	 * Lista os treinos já realizados ou de datas anteriores 
	 * 
	 * @param dataInicio
	 * @param dataTermino
	 * @param idAluno
	 * @param pageRequest
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<Treino> listTreinoDataHistoricoByFilters(
		LocalDate dataInicio ,
		LocalDate dataTermino ,
		Long idAluno,
		PageRequest pageRequest
	){
		
		Assert.notNull(
			dataInicio, 
			MessageSourceHolder.translate("service.treino.data.informe.data.inicio")
		);
		
		Assert.notNull(
				dataTermino, 
			MessageSourceHolder.translate("service.treino.data.informe.data.termino")
		);
		
		Assert.notNull(
			idAluno,
			MessageSourceHolder.translate("service.treino.data.informe.codigo.aluno")
		);
		
		return this.treinoRepository.listByFiltersHistorico(
			dataInicio, 
			dataTermino, 
			idAluno,
			pageRequest
		);
		
	}
	
	/**
	 * Remove um treino por id
	 * @param id
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public void deleteTreino( long id ){
		this.treinoRepository.deleteById(id);
	}
	
	
}
