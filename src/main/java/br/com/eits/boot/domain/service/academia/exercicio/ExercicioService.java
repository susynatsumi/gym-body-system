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
import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.repository.academia.exercicio.IExercicioRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

@Service
@RemoteProxy
@Transactional
public class ExercicioService {

	// -------------------------------------------------
	// ------------ ATRIBUTOS --------------------------
	// -------------------------------------------------

	@Autowired
	private IExercicioRepository exercicioRepository;
	
	@Autowired
	private ImagemConverter imageconverter;
	
	// -------------------------------------------------
	// -------------MÉTODOS ----------------------------
	// -------------------------------------------------
	
	/**
	 * Insere um novo exercício 
	 * 
	 * @param exercicio
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Exercicio insertExercicio( Exercicio exercicio ) {
		
		Assert.notNull(
			exercicio,
			MessageSourceHolder.translate("service.object.null")
		);
		
		Assert.isNull(
			exercicio.getId(),
			MessageSourceHolder.translate("service.object.id.null")
		);
		
		Assert.notEmpty(
			exercicio.getExercicioGrupoMusculares(),
			MessageSourceHolder.translate("exercicio.service.grupo.muscular.is.empty")
		);
		
		exercicio.setIsAtivo(true);
		
		exercicio.getExercicioGrupoMusculares()
			.forEach(grupoMuscular -> {
				grupoMuscular.setExercicio(exercicio);
			});
		
		byte[] imagem = this.imageconverter
			.fileTransferToByteArray(
					exercicio.getImagemFileTransfer()
			);

		exercicio.setImagem(imagem);
		
		return this.exercicioRepository.save( exercicio );
		
	}
	
	/**
	 * Faz update no exercicio
	 * 
	 * @param exercicio
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Exercicio updateExercicio( Exercicio exercicio ){

		Assert.notNull(
			exercicio,
			MessageSourceHolder.translate("exercicio.service.null")
		);
		
		Assert.notNull(
			exercicio.getId(),
			MessageSourceHolder.translate("exercicio.service.id.not.null")
		);
		
		Assert.notEmpty(
			exercicio.getExercicioGrupoMusculares(),
			MessageSourceHolder.translate("exercicio.service.grupo.muscular.is.empty")
		);
			
		exercicio.getExercicioGrupoMusculares()
			.forEach(grupoMuscular -> {
				grupoMuscular.setExercicio(exercicio);
		});
		
		byte[] imagem = this.imageconverter
				.fileTransferToByteArray(
						exercicio.getImagemFileTransfer()
				);
		
		if(imagem != null){
			exercicio.setImagem(imagem);
		}
		
		return this.exercicioRepository.save( exercicio );
		
	}
	
	/**
	 * Busca um determinado exercicio por id
	 * 
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Exercicio findExercicioById( long id ){
		
		return this.exercicioRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(
					MessageSourceHolder.translate(
						"repository.notFoundById", id
					)
				)
			);
	}
	
	/**
	 * Lista exercicios de acordo com os filtros 
	 * 
	 * @param filtros
	 * @param isAtivo
	 * @param pageable
	 * @return
	 * 
	 */
	@Transactional( readOnly = true )
	public Page<Exercicio> listExerciciosByFilters(
		String filtros,
		Boolean isAtivo,
		PageRequest pageable
	){
		return this.exercicioRepository.listByFilters(filtros, isAtivo, pageable);
	}
	
	/**
	 * Faz delete de um exercicio de acordo com seu id
	 * @param id
	 */
	public void deleteExercicio( Long id ){
		this.exercicioRepository.deleteById(id);
	}
	
}
