package br.com.eits.boot.domain.repository.academia.treino;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.treino.TreinoExercicio;

public interface ITreinoExercicioRepository extends JpaRepository<TreinoExercicio, Long>{

	
	@EntityGraph(attributePaths={
		"treino.id",
		"exercicio.id"
	})
	@Override
	Optional<TreinoExercicio> findById(Long id) ;
	
}
