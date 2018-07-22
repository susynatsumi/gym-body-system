package br.com.eits.boot.domain.repository.academia.treino;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioTreinoData;

public interface IExercicioTreinoDataRepository extends JpaRepository<ExercicioTreinoData, Long>{


	@Query(
		  " FROM "
		+ "		ExercicioTreinoData exercicioTreinoData "
		+ "	WHERE "
		+ "		exercicioTreinoData.treinoData.treino.id = :idTreino "
	)
	List<ExercicioTreinoData> listExercicioTreinoDataByTreinoId(@Param("idTreino") Long idTreino );
	
}
