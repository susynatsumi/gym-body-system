package br.com.eits.boot.domain.repository.academia.exercicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.exercicio.Equipamento;

public interface IEquipamentoRepository extends JpaRepository<Equipamento, Long>{

	@Query(
		" FROM "
		+ "	Equipamento equipamento "
		+ "WHERE "
		+ "	filter( :filter, equipamento.id, equipamento.descricao ) = TRUE "
		+ "	AND ( "
		+ "		:isAtivo IS NULL "
		+ "		OR equipamento.isAtivo = :isAtivo "
		+ " ) "
	)
	Page<Equipamento> listByFilters(
		@Param("filter") String filtro,
		@Param("isAtivo") Boolean isAtivo,
		Pageable pageable
	);
	
}
