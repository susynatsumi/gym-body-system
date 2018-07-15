package br.com.eits.boot.domain.service.academia.pessoa;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.pessoa.PessoaAcademia;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.pessoa.IPessoaAcademiaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class PessoaAcademiaService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IPessoaAcademiaRepository pessoaAcademiaRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere uma pessoa academia na base de dados
	 * 
	 * @param pessoaAcademia
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public PessoaAcademia insertPessoaAcademia( 
		PessoaAcademia pessoaAcademia 
	){
		Assert.notNull(
			pessoaAcademia,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			pessoaAcademia.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.pessoaAcademiaRepository.save(pessoaAcademia);
	}
	
	/**
	 * Realiza update de uma pessoa academia 
	 * 
	 * @param pessoaAcademia
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public PessoaAcademia updatePessoaAcademia(
		PessoaAcademia pessoaAcademia 
	){
		
		Assert.notNull(
			pessoaAcademia,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			pessoaAcademia.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.pessoaAcademiaRepository.save(pessoaAcademia);
	}
	
	/**
	 * 
	 * Busca pessoa academia por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public PessoaAcademia findPessoaAcademiaById( Long id ){
		return this.pessoaAcademiaRepository
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
