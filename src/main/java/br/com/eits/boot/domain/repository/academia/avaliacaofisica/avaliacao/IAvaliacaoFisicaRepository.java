package br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.avaliacaofisica.avaliacao.AvaliacaoFisica;

public interface IAvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisica, Long>{

	
	@EntityGraph(attributePaths ={
		"pessoa",
		"perimetria",
		"resposta",
		"avaliacaoAntropometrica.dobrasCutaneas",
		"avaliacaoAntropometrica.indiceMassaCorporal",
		"avaliacaoAntropometrica.predicaoGorduraSiri"
	})
	@Override
	Optional<AvaliacaoFisica> findById(Long id);

	@EntityGraph(attributePaths ={
		"pessoa",
		"perimetria",
		"resposta",
		"avaliacaoAntropometrica.dobrasCutaneas",
		"avaliacaoAntropometrica.indiceMassaCorporal",
		"avaliacaoAntropometrica.predicaoGorduraSiri"
	})
	@Query(
		" FROM AvaliacaoFisica avaliacaoFisica "
		+ "	WHERE "
		+ "	filter(:filter, avaliacaoFisica.id, avaliacaoFisica.pessoa.nome) = TRUE "
		+ "	AND ( "
		+ "		cast( :dataInicio as date ) is null "
		+ "		OR avaliacaoFisica.data >= :dataInicio  "
		+ "	) "
		+ "	AND ( "
		+ "		cast( :dataFim as date ) is null	"
		+ "		OR avaliacaoFisica.data <= :dataFim "
		+ "	) AND ( "
		+ "		:idPessoa is null "
		+ "		OR avaliacaoFisica.pessoa.id = :idPessoa "
		+ "	) "
	)
	Page<AvaliacaoFisica> listAvaliacaoFisicaByFilters(
		@Param("filter") String filter, 
		@Param("idPessoa") Long idPessoa,
		@Param("dataInicio") LocalDate dataInicio, 
		@Param("dataFim") LocalDate dataFim, 
		Pageable pageRequest
	);

	@EntityGraph(attributePaths ={
		"pessoa",
		"perimetria",
		"resposta",
		"avaliacaoAntropometrica.dobrasCutaneas",
		"avaliacaoAntropometrica.indiceMassaCorporal",
		"avaliacaoAntropometrica.predicaoGorduraSiri"
	})
	Page<AvaliacaoFisica> findAvaliacaoFisicaByPessoa_id(Long idPessoa, Pageable pageRequest);
	
}
