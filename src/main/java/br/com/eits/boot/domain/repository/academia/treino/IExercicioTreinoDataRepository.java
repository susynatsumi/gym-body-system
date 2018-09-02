package br.com.eits.boot.domain.repository.academia.treino;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioRealizado;

public interface IExercicioTreinoDataRepository extends JpaRepository<ExercicioRealizado, Long>{


	@Query(
		  " FROM "
		+ "		ExercicioRealizado exercicioTreinoData "
		+ "	WHERE "
		+ "		exercicioTreinoData.treinoData.treino.id = :idTreino "
	)
	List<ExercicioRealizado> listExercicioTreinoDataByTreinoId(@Param("idTreino") Long idTreino );
	
}
