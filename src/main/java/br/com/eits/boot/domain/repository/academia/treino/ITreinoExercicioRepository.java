package br.com.eits.boot.domain.repository.academia.treino;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;

public interface ITreinoExercicioRepository extends JpaRepository<TreinoExercicio, Long>{

	
	@EntityGraph(attributePaths={
		"treino",
		"exercicio"
	})
	@Override
	Optional<TreinoExercicio> findById(Long id) ;

	@EntityGraph(attributePaths = {
		"exercicio",
		"exercicio.equipamento",
		"exercicio.exercicioGrupoMusculares",
		"treino"
	})
	Page<TreinoExercicio> findByTreino_id(Long idTreino, Pageable pageable);
	
}
