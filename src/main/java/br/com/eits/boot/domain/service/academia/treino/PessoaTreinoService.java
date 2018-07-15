package br.com.eits.boot.domain.service.academia.treino;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.treino.PessoaTreino;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.treino.IPessoaTreinoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class PessoaTreinoService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IPessoaTreinoRepository pessoaTreinoRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere uma pessoa treino na base de dados
	 * 
	 * @param pessoaTreino
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public PessoaTreino insertPessoaTreino(
		PessoaTreino pessoaTreino
	){
		
		Assert.notNull(
			pessoaTreino,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			pessoaTreino.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.pessoaTreinoRepository.save(pessoaTreino);
	}
	
	/**
	 * Realiza update de uma pessoa treino 
	 * 
	 * @param pessoaTreino
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public PessoaTreino updatePessoaTreino(
		PessoaTreino pessoaTreino 
	){
		
		Assert.notNull(
			pessoaTreino,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			pessoaTreino.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.pessoaTreinoRepository.save(pessoaTreino);
	}
	
	/**
	 * 
	 * Busca pessoa treino por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public PessoaTreino findPessoaTreinoById( Long id ){
		
		return this.pessoaTreinoRepository
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
