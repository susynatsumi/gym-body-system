package br.com.eits.boot.domain.service.academia.treino;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere um treino data na base de dados
	 * 
	 * @param treinoData
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public TreinoData insertTreinoData(
		TreinoData treinoData
	){
		
		Assert.notNull(
			treinoData,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			treinoData.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.treinoDataRepository.save(treinoData);
	}
	
	/**
	 * Realiza update de um treino data 
	 * 
	 * @param treinoData
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
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
	public TreinoData findTreinoById( Long id ){
		
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
	
}
