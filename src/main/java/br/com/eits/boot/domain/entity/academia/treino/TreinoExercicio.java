package br.com.eits.boot.domain.entity.academia.treino;

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

import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;
import br.com.eits.common.domain.entity.AbstractEntity;
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
public class TreinoExercicio extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2643621014184247216L;

	// ---------------------------------
	// ------- ATRIBUTOS ---------------
	// ---------------------------------
	
	// quantidade de séries para execução do exercicio com o tio de carga
	@Min( value = 1) 
	@NotNull
	@Column( nullable = false)
	private Integer series;
	
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
	@Column( length = 500 )
	private String observacoes;
	
	// treino ao qual o exercicio pertence
	@NotNull
	@ManyToOne( 
		cascade = CascadeType.MERGE,
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = Treino.class
	)
	private Treino treino;
	
	// tipo do exercicio do treino
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	@Column(nullable = false )
	private TipoTreinoExercicio tipoTreinoExercicio;
	
	// exercicio do treino
	@NotNull
	@ManyToOne(
//		cascade = CascadeType.MERGE,
		fetch = FetchType.EAGER,
		optional = false,
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
	 * @param id
	 * @param series
	 * @param carga
	 * @param repeticoes
	 * @param tempoMin
	 * @param observacoes
	 * @param isAtivo
	 * @param treino
	 * @param tipoTreinoExercicio
	 * @param exercicio
	 */
	public TreinoExercicio(
		Long id, 
		Integer series, 
		Integer carga, 
		Integer repeticoes,
		Integer tempoMin, 
		String observacoes, 
		Treino treino,
		Exercicio exercicio,
		TipoTreinoExercicio tipoTreinoExercicio 
	) {
		super(id);
		this.series = series;
		this.carga = carga;
		this.repeticoes = repeticoes;
		this.tempoMin = tempoMin;
		this.observacoes = observacoes;
		this.treino = treino;
		this.tipoTreinoExercicio = tipoTreinoExercicio;
		this.exercicio = exercicio;
	}
	
}
