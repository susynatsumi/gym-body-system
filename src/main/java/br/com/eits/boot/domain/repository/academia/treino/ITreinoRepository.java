package br.com.eits.boot.domain.repository.academia.treino;

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
		"treinoExercicios",
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
	
}
