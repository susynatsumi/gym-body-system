package br.com.eits.boot.domain.repository.academia.treino;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.treino.TreinoData;

public interface ITreinoDataRepository extends JpaRepository<TreinoData, Long>{

	@EntityGraph(attributePaths={
		"exerciciosRealizados"
	})
	List<TreinoData> findByTreino_Id( Long id );

	@Query("FROM "
		+ "		TreinoData treinoData "
		+ "WHERE "
		+ "		treinoData.data >= :dataInicio"
		+ "		AND treinoData.treino.aluno.id = :idAluno "
		+ "		AND ( "
		+ "			:somenteCompletos IS NULL "
		+ "			OR treinoData.completo = :somenteCompletos	"
		+ "		) "
	)
	Page<TreinoData> listByFilters(
		@Param("dataInicio") LocalDate dataInicio, 
		@Param("idAluno") Long idAluno,
		@Param("somenteCompletos") Boolean somenteCompletos,
		Pageable pageRequest
	);
	
//	verificar se está salvando e buscando novamente todos os atributos
	@EntityGraph(attributePaths={
		"exerciciosRealizados.treinoExercicio.exercicio"
	})
	@Override
	Optional<TreinoData> findById(Long id) ;
	
}
