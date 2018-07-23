package br.com.eits.boot.domain.repository.academia;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.Academia;

public interface IAcademiaRepository extends JpaRepository<Academia, Long>{

	@Query(
		" FROM "
		+ "		Academia academia "
		+ " WHERE "
		+ "		filter(:filters, academia.id, academia.razaoSocial, academia.cidade) = TRUE "
//		+ "		( "
//		+ "			:filters IS NULL  "
//		+ "			OR :filters = '' "
//		+ "			OR unaccent( UPPER(academia.razaoSocial)) like unaccent( UPPER('%' || cast(:filters as text) || '%') ) "
//		+ "		) "
	)
	Page<Academia> listByFilters(@Param("filters") String filters, Pageable pageable);

	@EntityGraph(attributePaths={
		"pessoaProprietario"
	})
	@Override
	Optional<Academia> findById(Long id) ;
	
}
