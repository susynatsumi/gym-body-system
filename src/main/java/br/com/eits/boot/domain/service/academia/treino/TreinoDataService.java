package br.com.eits.boot.domain.service.academia.treino;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.DiaSemana;
import br.com.eits.boot.domain.entity.academia.treino.Treino;
import br.com.eits.boot.domain.entity.academia.treino.TreinoData;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.treino.ITreinoDataRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class TreinoDataService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private ITreinoDataRepository treinoDataRepository;
	
//	@Autowired
//	private ExercicioRealizadoService exercicioTreinoDataService;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere um treino data na base de dados
	 * 
	 * @param treinoData
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public TreinoData insertTreinoData(
		TreinoData treinoData
	){
		
		Assert.notNull(
			treinoData,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			treinoData.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		treinoData.setCompleto(false);
		
		return this.treinoDataRepository.save(treinoData);
	}
	
	/**
	 * Realiza update de um treino data 
	 * 
	 * @param treinoData
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public TreinoData updateTreinoData(
			TreinoData treinoData 
	){
		
		Assert.notNull(
			treinoData,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			treinoData.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		Assert.notNull(
			treinoData.getExerciciosRealizados(),
			MessageSourceHolder.translate("service.exercicio.realizado.exercicios.empty")
		);
		
		treinoData.setCompleto(treinoData.completouTreinoDoDia());
		
		treinoData.getExerciciosRealizados().forEach(
		exercicio ->{
			exercicio.setTreinoData(treinoData);
		});
		
		return this.treinoDataRepository.save(treinoData);
	}
	
	
	/**
	 * 
	 * Busca treino data por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public TreinoData findTreinoDataById( Long id ){
		
		return this.treinoDataRepository
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
	 * Gera as datas de treino de acordo com os dias da semana selecionados para o treino 
	 * e o intervalo de datas
	 * @param treino
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public void criaDatasTreino(Treino treino) {
			
		LocalDate dataAtual = treino.getDataInicio();
		final List<DiaSemana> diasSemana = treino.getDiasSemanaSelecionados();
		
		List<TreinoData> datasDoTreino = new ArrayList<TreinoData>();
		
		do {
			final LocalDate data = dataAtual;
			final Optional<DiaSemana> diaSemanaAtual = diasSemana.stream()
				.filter(diaSemana -> 
					diaSemana.getDayOfWeek().equals(String.valueOf(data.getDayOfWeek()))
				)
				.findFirst();
			
			if(diaSemanaAtual.isPresent()){

				TreinoData treinoData = new TreinoData(
					dataAtual, // data treino
					null,// hora inicio
					null,// hora termino
					false, // se está completo
					treino, // treino
					diaSemanaAtual.get() // dia da semana 
				);
				
				datasDoTreino.add(treinoData);
				
			}
			
			dataAtual = dataAtual.plusDays(1);
			
		} while( !dataAtual.isAfter(treino.getDataFim()) );
		
		Assert.notEmpty(
			datasDoTreino,
			MessageSourceHolder.translate("service.treino.insert.dias.semana.empty")
		);

		datasDoTreino.forEach(treinoData ->{
			this.insertTreinoData(treinoData);
		});
		
		
	}

	/**
	 * 
	 * utilizar o método listTreinosComDatasByFilters do treino Service
	 * 
	 * Lista de acordo com os filtros
	 * 
	 * @param dataInicio
	 * @param dataTermino
	 * @param idAluno
	 * @param somenteCompletos
	 * @param pageRequest
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<TreinoData> listTreinoDataByFilters(
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
		
		return this.treinoDataRepository.listByFilters(dataInicio, dataTermino, idAluno, somenteCompletos ,pageRequest);
	}
	
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<TreinoData> listTreinoDataHistoricoByFilters(
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
			
			return this.treinoDataRepository.listByFiltersHistorico(dataInicio, dataTermino, idAluno ,pageRequest);
		}
	
}
