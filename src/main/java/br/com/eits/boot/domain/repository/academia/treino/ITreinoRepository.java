package br.com.eits.boot.domain.repository.academia.treino;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.treino.Treino;

public interface ITreinoRepository extends JpaRepository<Treino, Long>{


	@EntityGraph(attributePaths= {
		"treinoExercicios.exercicio",
		"aluno",
		"personal"
	})
	@Override
	Optional<Treino> findById(Long id);

	@EntityGraph(
		attributePaths={
			"aluno.nome"
		}
	)
	@Query(
		" FROM Treino treino "
		+ "	WHERE "
		+ "		filter(:filters, treino.nome, treino.aluno.nome, treino.id ) = TRUE "
	)
	Page<Treino> listTreinosByFilters( 
		@Param("filters") String filters, 
		Pageable pageRequest
	);

	@EntityGraph(attributePaths = {
		"treinoDatas"
	})
	@Query(
	   " FROM "
	 + "	Treino treino "
	 + "	JOIN treino.treinoDatas treinoData "
	 + " WHERE "
	 + "	treinoData.data BETWEEN :dataInicio and :dataTermino "
	 + "	AND treino.aluno.id = :idAluno "
	 + " 	AND ( "
	 + "		:somenteCompletos IS NULL "
	 + "		OR treinoData.completo = :somenteCompletos	"
	 + "	) " 		
	)
	Page<Treino> listTreinosComDatasByFilters(
		@Param("idAluno") Long idAluno, 
		@Param("dataInicio") LocalDate dataInicio, 
		@Param("dataTermino") LocalDate dataTermino,
		@Param("somenteCompletos") Boolean somenteCompletos, 
		Pageable pageRequest
	);
	
	@EntityGraph(attributePaths ={
		"treinoDatas"
	})
	@Query(
		  " FROM "
		+ "		Treino treino "
		+ "		JOIN treino.treinoDatas as treinoData "
		+ "	WHERE "
		+ "		treino.aluno.id = :idAluno "
		+ "		AND treinoData.data BETWEEN :dataInicio and :dataTermino "
		+ "		AND ( "
		+ "			treinoData.completo = true "
		+ "			OR treinoData.data < now() "
		+ "		) "
	)
	Page<Treino> listByFiltersHistorico(
		@Param("dataInicio") LocalDate dataInicio, 
		@Param("dataTermino") LocalDate dataTermino,
		@Param("idAluno") Long idAluno,
		Pageable pageRequest
	);
	
}
