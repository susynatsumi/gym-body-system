package br.com.eits.boot.domain.repository.academia.exercicio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.exercicio.Exercicio;

public interface IExercicioRepository extends JpaRepository<Exercicio, Long>{


	@EntityGraph(attributePaths = {
		"equipamento.id",
		"equipamento.descricao",
		"exercicioGrupoMusculares"
	})
	@Query(
		" FROM Exercicio exercicio where exercicio.id = :id "
	)
	@Override
	Optional<Exercicio> findById(@Param("id") Long id);
	
	@Query(
		" FROM "
		+ "	Exercicio exercicio "
		+ "WHERE "
		+ "	filter( :filtros, exercicio.id, exercicio.nome, exercicio.equipamento.descricao ) = TRUE "
		+ "	AND ( "
		+ "		:isAtivo IS NULL "
		+ " 	OR exercicio.isAtivo = :isAtivo "
		+ "	) "
	)
	Page<Exercicio> listByFilters(
		@Param("filtros") String filtros, 
		@Param("isAtivo") Boolean isAtivo, 
		Pageable pageable
	);
	
}
