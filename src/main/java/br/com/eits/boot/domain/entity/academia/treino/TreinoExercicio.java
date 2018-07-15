package br.com.eits.boot.domain.entity.academia.treino;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;

import br.com.eits.boot.domain.entity.academia.AbstractEntityAcademia;
import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(uniqueConstraints = {
	@UniqueConstraint( columnNames = {"exercicio_id", "treino_id"} )
})
@Entity
@Audited
@Data
@EqualsAndHashCode( callSuper = true )
@DataTransferObject
public class TreinoExercicio extends AbstractEntityAcademia {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2643621014184247216L;

	// ---------------------------------
	// ------- ATRIBUTOS ---------------
	// ---------------------------------
	
	// data de inativação, caso esteja inativo
	@Column
	private LocalDateTime dataInativacao;
	
	// peso em quilogramas que sera utilizado pelo aluno no treino
	@Min(value = 0)
	@Column
	private Integer carga;
	
	// quantidade de repetições no exercicio
	@Min( value = 0 )
	@Column
	private Integer repeticoes;
	
	// tempo em minutos para realizacao de um exercicio no treino
	@Min( value  = 0 )
	@Column
	private Integer tempoMin;
	
	// observacoes sobre o exercicio do treino, caso for necessario
	@Column( length = 150 )
	private String observacoes;
	
	// se o exercicio está ativo dentro do treino, se nao estiver
	// não deve ser apresentado
	@NotNull
	@Column( nullable = false )
	private Boolean isAtivo;
	
	// treino ao qual o exercicio pertence
	@NotNull
	@ManyToOne( 
		fetch = FetchType.LAZY,
		optional = false,
		cascade = CascadeType.REFRESH,
		targetEntity = Treino.class
	)
	private Treino treino;
	
	// exercicio do treino
	@NotNull
	@ManyToOne(
		fetch = FetchType.LAZY,
		optional = false,
		cascade = CascadeType.REFRESH,
		targetEntity = Exercicio.class
	)
	private Exercicio exercicio;
	
	// -----------------------------------------
	// ---------- CONSTRUTORES -----------------
	// -----------------------------------------
	
	/**
	 * Constructor Default
	 */
	public TreinoExercicio(){
		super();
	}
	
	/**
	 * Construtor somente com id
	 * 
	 * @param id
	 */
	public TreinoExercicio( Long id ){
		super(id);
	}

	/**
	 * 
	 * Construtor com todos os fields
	 * 
	 * @param dataInativacao
	 * @param carga
	 * @param repeticoes
	 * @param tempoMin
	 * @param observacoes
	 * @param isAtivo
	 * @param treino
	 * @param exercicio
	 */
	public TreinoExercicio(
		LocalDateTime dataInativacao,
		Integer carga, 
		Integer repeticoes,
		Integer tempoMin, 
		String observacoes, 
		Boolean isAtivo, 
		Treino treino,
		Exercicio exercicio
	) {
		super();
		this.dataInativacao = dataInativacao;
		this.carga = carga;
		this.repeticoes = repeticoes;
		this.tempoMin = tempoMin;
		this.observacoes = observacoes;
		this.isAtivo = isAtivo;
		this.treino = treino;
		this.exercicio = exercicio;
	}
	
}
