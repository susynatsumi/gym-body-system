package br.com.eits.boot.domain.service.academia;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.Academia;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.IAcademiaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class AcademiaService {

	// ------------------------------------
	// ------------ ATRIBUTOS -------------
	// ------------------------------------
	
	@Autowired
	private IAcademiaRepository academiaRepository;
	
	// ------------------------------------
	// -------------- MÉTODOS -------------
	// ------------------------------------
	
	/**
	 * Insere uma academia na base de dados
	 * 
	 * @param academia
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "')")
	public Academia insertAcademia(
		Academia academia
	){
		
		Assert.notNull(
			academia,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			academia.getId(),
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		academia.setIsAtiva(true);
		
		return this.academiaRepository.save(academia);
	}
	
	/**
	 * Realiza update de uma academia 
	 * 
	 * @param academia
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "')")
	public Academia updateAcademia(
		Academia academia 
	){
		
		Assert.notNull(
			academia,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.notNull(
			academia.getId(),
			MessageSourceHolder.translate("service.object.null")
		);
		
		return this.academiaRepository.save(academia);
	}
	
	/**
	 * 
	 * Busca academia por id
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "')")
	@Transactional( readOnly = true )
	public Academia findAcdemiaById( Long id ){
		
		final Academia acade = this.academiaRepository
		.findById(id)
		.orElseThrow(() ->
			new IllegalArgumentException(
				MessageSourceHolder.translate(
					"repository.notFoundById", id
				)
			)
		);
		return acade;
//		return this.academiaRepository
//				.findById(id)
//				.orElseThrow(() ->
//					new IllegalArgumentException(
//						MessageSourceHolder.translate(
//							"repository.notFoundById", id
//						)
//					)
//				);
		
	}
	
	/**
	 * Listagem de academia por filtros
	 * 
	 * @param filters
	 * @param pageable
	 * @return
	 */
	@Transactional( readOnly = true )
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "')")
	public Page<Academia> listAcademiaByFilters(String filters , PageRequest pageable){
		return this.academiaRepository.listByFilters(filters, pageable);
	}
	
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "')")
	public void deleteAcademia( Long id ){
		this.academiaRepository.deleteById(id);
	}
	
}
