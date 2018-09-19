package br.com.eits.boot.domain.repository.academia.treino;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.treino.TreinoData;

public interface ITreinoDataRepository extends JpaRepository<TreinoData, Long>{

	@EntityGraph(attributePaths={
		"exerciciosTreinoDatas"
	})
	List<TreinoData> findByTreino_Id( Long id );

	@EntityGraph(attributePaths={
		"treino"
	})
	@Query("FROM "
		+ "		TreinoData treinoData "
		+ "WHERE "
		+ "		treinoData.data >= :dataInicio"
		+ "		AND treinoData.treino.aluno.id = :idAluno "
	)
	Page<TreinoData> listByFilters(
		@Param("dataInicio") LocalDate dataInicio, 
		@Param("idAluno") Long idAluno,
		Pageable pageRequest
	);
	
}
