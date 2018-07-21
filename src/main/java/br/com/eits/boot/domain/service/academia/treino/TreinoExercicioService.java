package br.com.eits.boot.domain.service.academia.treino;

import java.time.LocalDateTime;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.TipoTreinoExercicio;
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
		
		treinoExercicio.setIsAtivo(true);
		
		validaTipoExercicioTreino(treinoExercicio);
		
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
		
		validaTipoExercicioTreino(treinoExercicio);
		
		return this.treinoExercicioRepository.save(treinoExercicio);
	}
	
	public TreinoExercicio inativarTreinoExercicio( Long id ){
		
		TreinoExercicio treinoExercicio = this.findTreinoExercicioById(id);
		
		Assert.notNull(
			treinoExercicio,
			MessageSourceHolder.translate("service.treino.exercicio.nao.encontrado")
		);
		
		treinoExercicio.setIsAtivo(false);
		treinoExercicio.setDataInativacao(LocalDateTime.now());
		
		return this.treinoExercicioRepository.save(treinoExercicio);
		
	}
	
	
	/**
	 * Valida alguns dados necessarios
	 * @param treinoExercicio
	 */
	private void validaTipoExercicioTreino(TreinoExercicio treinoExercicio){
		
		Assert.isTrue(
			treinoExercicio.getIsAtivo(),
			MessageSourceHolder.translate("service.treino.exercicio.status.inativo")
		);
		
		if(TipoTreinoExercicio.CARGA_REPETICOES.equals(treinoExercicio.getTipoTreinoExercicio())){
			Assert.isTrue(
				treinoExercicio.getCarga() != null 
				&& treinoExercicio.getCarga().intValue() != 0 
				&& treinoExercicio.getRepeticoes() != null
				&& treinoExercicio.getRepeticoes().intValue() != 0,
				MessageSourceHolder.translate("service.treino.exercicio.carga.repeticoes")
			);
		} else if(TipoTreinoExercicio.REPETICOES.equals(treinoExercicio.getTipoTreinoExercicio())){
			Assert.isTrue(
				treinoExercicio.getRepeticoes() != null
				&& treinoExercicio.getRepeticoes().intValue() != 0,
				MessageSourceHolder.translate("service.treino.exercicio.repeticoes")
			);
		} else if(TipoTreinoExercicio.TEMPO.equals(treinoExercicio.getTipoTreinoExercicio())){
			Assert.isTrue(
				treinoExercicio.getTempoMin() != null 
				&& treinoExercicio.getTempoMin().intValue() != 0,
				MessageSourceHolder.translate("service.treino.exercicio.tempo.min")
			);
		}
		
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