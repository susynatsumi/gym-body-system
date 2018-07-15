package br.com.eits.boot.domain.entity.academia.exercicio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class Exercicio extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5765212522615276733L;

	
	// ---------------------------------
	// -------- ATRIBUTOS --------------
	// ---------------------------------
	
	// Nome do exercício
	@NotBlank
	@Column(nullable = false, length = 60, unique = true )
	private String nome;
	
	// Descricao do exercícios, texto longo
	@NotBlank 
	@Column(nullable = false , length = 255)
	private String descricao;
	
	// Link do vídeo do youtube
	@Column(nullable = true)
	private String linkVideo;
	
	// status do exercicios, se estiver inativo 
	// não deve aparecer pra montagem de treinos
	@Column(nullable = false)
	private Boolean isAtivo;

	// ---------------------------------------
	// ------------ CONSTRUCTORS -------------
	// ---------------------------------------
	
	/**
	 * Constructor Default
	 */
	public Exercicio(){
		super();
	}
	
	/**
	 * Constructor com id
	 * 
	 * @param id
	 */
	public Exercicio( Long id ){
		super(id);
	}
	
	/**
	 * 
	 * Constructor usando todos os fields
	 * 
	 * @param nome
	 * @param descricao
	 * @param linkVideo
	 * @param isAtivo
	 */
	public Exercicio(@NotBlank String nome, @NotBlank String descricao, String linkVideo, Boolean isAtivo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.linkVideo = linkVideo;
		this.isAtivo = isAtivo;
	}
	
	
}
