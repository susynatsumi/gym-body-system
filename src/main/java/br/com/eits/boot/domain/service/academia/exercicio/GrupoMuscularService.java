package br.com.eits.boot.domain.service.academia.exercicio;

import java.util.Collection;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.exercicio.GrupoMuscular;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.exercicio.IGrupoMuscularRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class GrupoMuscularService {

	// --------------------------------------------
	// ------------------ ATRIBUTOS ---------------
	// --------------------------------------------
	
	@Autowired
	private IGrupoMuscularRepository grupoMuscularRepository;
	
	// --------------------------------------------
	// -------------------MÉTODOS -----------------
	// --------------------------------------------
	
	/**
	 * Insere os dados de um grupo muscular 
	 * 
	 * @param grupoMuscular
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public GrupoMuscular insertGrupoMuscular( GrupoMuscular grupoMuscular ){
		
		Assert.notNull(
			grupoMuscular,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.isNull(
			grupoMuscular.getId(),
			MessageSourceHolder.translate("exercicio.service.id.null")
		);
		
		return this.grupoMuscularRepository.save(grupoMuscular);
		
	}

	/**
	 * Realiza update de um grupo muscular
	 * @param grupoMuscular
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public GrupoMuscular updateGrupoMuscular( GrupoMuscular grupoMuscular ){
		
		Assert.notNull(
			grupoMuscular,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			grupoMuscular.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		return this.grupoMuscularRepository.save( grupoMuscular );
		
	}
	
	/**
	 * 
	 * Realiza busca de grupo muscular por id
	 * 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	@Transactional( readOnly = true )
	public GrupoMuscular findGrupoMuscularById(
		long id
	) {
		
		return this.grupoMuscularRepository
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
	 * Lista grupos musculares de acordo com o filtro 
	 * 
	 * @param filtros
	 * @param pageRequest
	 * @return
	 */
//	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
//	@Transactional( readOnly = true )
//	public Page<GrupoMuscular> listByFilters(String filtros, PageRequest pageRequest){
//		return this.grupoMuscularRepository.listByFilters(filtros, pageRequest);
//	}
//	
	
	/**
	 * Lista grupos musculares de acordo com o filtro, removendo registros de ids passados como parametro 
	 * 
	 * @param filtros
	 * @param pageRequest,
	 * @param idsNotIn,
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<GrupoMuscular> listByFilters(String filtros, Collection<Long> idsNotIn, PageRequest pageRequest){

		return this.grupoMuscularRepository.listByFilters(filtros, idsNotIn , pageRequest);
	}
	
	/**
	 * 
	 * Remove um grupo muscular de acordo com o id informado 
	 * 
	 * @param id
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public void deleteGrupoMuscular( Long id ) {
		this.grupoMuscularRepository.deleteById( id );
	}
	
}
