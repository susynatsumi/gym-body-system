package br.com.eits.boot.domain.service.academia.exercicio;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.application.converter.ImagemConverter;
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
	
	@Autowired
	private ImagemConverter imageconverter;
	
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
		
		byte[] imagem = this.imageconverter
			.fileTransferToByteArray(
				equipamento.getImagemFileTransfer()
			);

		equipamento.setImagem(imagem);
		
		return this.equipamentoRepository.save( equipamento );
		
	}
	
	/**
	 * Atualiza os dados do equipamento  
	 * 
	 * @param equipamento
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Equipamento updateEquipamento( Equipamento equipamento ) {
		
		Assert.notNull( 
			equipamento, 
			MessageSourceHolder.translate("service.object.null")
		);
			
		Assert.notNull(
			equipamento.getId(),
			MessageSourceHolder.translate("service.object.id.not.null")
		);
		
		byte[] imagem = this.imageconverter
				.fileTransferToByteArray(
					equipamento.getImagemFileTransfer()
				);
		
		if(imagem != null){
			equipamento.setImagem(imagem);
		} else {
			Equipamento equipamento2 = this.findEquipamentoById(equipamento.getId());
			equipamento.setImagem(equipamento2.getImagem());
		}
		
		return this.equipamentoRepository.save( equipamento );
		
	}
	
	/**
	 * 
	 * Remove imagem de um equipamento
	 * 
	 * @param equipamentoId
	 * 
	 */
	public void removerImagem(long equipamentoId){
		
		Equipamento equipamento = this.findEquipamentoById(equipamentoId);
		
		equipamento.setImagem(null);
		
		this.equipamentoRepository.save(equipamento);
		
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
	public Page<Equipamento> listEquipamentoByFilters( 
		String filtro,
		Boolean isAtivo,
		PageRequest pageable 
	){
		return this.equipamentoRepository
				.listByFilters(
					filtro, 
					isAtivo, 
					pageable
				);
	}
	
	/**
	 * Faz a remoção de equipamentos pelo id
	 * @param id
	 */
	public void deleteEquipamento( Long id ){
		
		this.equipamentoRepository
			.deleteById(id);
		
	}
	
}
