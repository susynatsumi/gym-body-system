package br.com.eits.boot.domain.repository.academia.exercicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.exercicio.GrupoMuscular;

public interface IGrupoMuscularRepository extends JpaRepository<GrupoMuscular, Long> {

	@Query(
		  " FROM "
		+ " 	GrupoMuscular grupoMuscular "
		+ "	WHERE "
		+ "		filter( :filtros, grupoMuscular.id, grupoMuscular.nome ) = TRUE "
	)
	Page<GrupoMuscular> listByFilters(@Param("filtros") String filtros, Pageable pageable);

	
	
}
