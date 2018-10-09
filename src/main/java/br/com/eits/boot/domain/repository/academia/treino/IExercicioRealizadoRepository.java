package br.com.eits.boot.domain.repository.academia.treino;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.eits.boot.domain.entity.academia.treino.ExercicioRealizado;

public interface IExercicioRealizadoRepository extends JpaRepository<ExercicioRealizado, Long>{


	@Query(
		  " FROM "
		+ "		ExercicioRealizado exercicioTreinoData "
		+ "	WHERE "
		+ "		exercicioTreinoData.treinoData.treino.id = :idTreino "
	)
	List<ExercicioRealizado> listExercicioTreinoDataByTreinoId(@Param("idTreino") Long idTreino );
	
	@EntityGraph(attributePaths ={
		"treinoExercicio.exercicio.equipamento",
		"treinoExercicio.exercicio.exercicioGrupoMusculares"
	})
	Page<ExercicioRealizado> findByTreinoData_id(Long idTreinoData, Pageable pageable);
	
}
