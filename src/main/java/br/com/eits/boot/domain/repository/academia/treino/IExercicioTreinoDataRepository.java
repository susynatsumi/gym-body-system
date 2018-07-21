package br.com.eits.boot.domain.repository.academia.treino;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioTreinoData;

public interface IExercicioTreinoDataRepository extends JpaRepository<ExercicioTreinoData, Long>{

//	@EntityGraph(
//		attributePaths={
//			"treinoData.id",
//			"treinoExercicio.id"
//		}
//	)
//	@Override
//	Optional<ExercicioTreinoData> findById(Long id) ;
	
}
