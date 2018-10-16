package br.com.eits.boot.domain.entity.academia.exercicio;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.io.FileTransfer;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Entity
@Audited
@Data
@EqualsAndHashCode( 
	callSuper = true,
	exclude = "exercicioGrupoMusculares"
)
@DataTransferObject
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=Exercicio.class)
public class Exercicio extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5765212522615276733L;

	
	// ---------------------------------
	// -------- ATRIBUTOS --------------
	// ---------------------------------
	
	// Nome do exercício
	@NotEmpty
	@Column(nullable = false, length = 60)
	private String nome;
	
	// Descricao do exercícios, texto longo
	@NotEmpty 
	@Column(nullable = false , length = 500)
	private String descricao;
	
//	// Link do vídeo do youtube
//	@Column(nullable = true)
//	private String linkVideo;
	
	// status do exercicios, se estiver inativo 
	// não deve aparecer pra montagem de treinos
	@Column(nullable = false)
	private Boolean isAtivo;
	
	@ManyToOne(
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = Equipamento.class
	)
	private Equipamento equipamento;

	@OneToMany(
		cascade = CascadeType.ALL,
		fetch = FetchType.LAZY,
		mappedBy = "exercicio",
		orphanRemoval = true, 
		targetEntity = ExercicioGrupoMuscular.class
	)
	private List<ExercicioGrupoMuscular> exercicioGrupoMusculares;
	
	// imagem ilustrativa do exercicio
	@Lob
	@Column
	private byte[] imagem;
	
	@Transient
	private FileTransfer imagemFileTransfer; 
	
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
	 * Constructor usando todos os fields
	 * 
	 * @param nome
	 * @param descricao
	 * @param linkVideo
	 * @param isAtivo
	 * @param equipamento
	 */
	public Exercicio(
		String nome, 
		String descricao,
		byte[] imagem,
		Boolean isAtivo,
		Equipamento equipamento
	) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.imagem = imagem;
		this.isAtivo = isAtivo;
		this.equipamento = equipamento;
	}
	
	
}
