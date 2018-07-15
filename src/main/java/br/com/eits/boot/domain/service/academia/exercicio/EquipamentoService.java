package br.com.eits.boot.domain.service.academia.exercicio;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.academia.exercicio.Equipamento;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.exercicio.IEquipamentoRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class EquipamentoService {

	// ---------------------------------------
	// ------------- ATRIBUTOS ---------------
	// ---------------------------------------
	
	
	@Autowired
	private IEquipamentoRepository equipamentoRepository;
	
	
	// -------------------------------------------
	// -----------  MÉTODOS ----------------------
	// -------------------------------------------
	
	
	/**
	 * Insere equipamento 
	 * 
	 * @param equipamento
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Equipamento insertEquipamento( Equipamento equipamento ) {
		
		Assert.notNull( 
			equipamento, 
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			equipamento.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);

		equipamento.setIsAtivo(true);
		
		return this.equipamentoRepository.save( equipamento );
		
	}
	
	/**
	 * Atualiza os dados do equipamento  
	 * 
	 * @param equipamento
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Equipamento update( Equipamento equipamento ) {
		
		Assert.notNull( 
			equipamento, 
			MessageSourceHolder.translate("service.object.null")
		);
			
		Assert.notNull(
			equipamento.getId(),
			MessageSourceHolder.translate("service.object.id.not.null")
		);

		return this.equipamentoRepository.save( equipamento );
		
	}
	
	/**
	 * Busca um equipamento por id 
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Equipamento findEquipamentoById( long id ){
		
		return this.equipamentoRepository
				.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
					MessageSourceHolder.translate("repository.notFoundById", id)
				));
	}
	
	/**
	 * Lista os equipamentos de acordo com os filtros 
	 * 
	 * @param filtro
	 * @param isAtivo
	 * @param pageable
	 * @return
	 */
	@Transactional( readOnly = true )
	public Page<Equipamento> listByEquipamentoFilters( 
		String filtro,
		Boolean isAtivo,
		Pageable pageable 
	){
		return this.equipamentoRepository
				.listByFilters(
					filtro, 
					isAtivo, 
					pageable
				);
	}
	
}
