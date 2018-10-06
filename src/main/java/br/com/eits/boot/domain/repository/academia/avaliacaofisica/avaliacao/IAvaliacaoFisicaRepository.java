package br.com.eits.boot.domain.repository.academia.avaliacaofisica.avaliacao;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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
	
}
