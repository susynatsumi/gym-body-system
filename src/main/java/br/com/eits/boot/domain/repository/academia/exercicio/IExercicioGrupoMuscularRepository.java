package br.com.eits.boot.domain.repository.academia.exercicio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.exercicio.ExercicioGrupoMuscular;

public interface IExercicioGrupoMuscularRepository extends JpaRepository<ExercicioGrupoMuscular, Long> {

	
	Page<ExercicioGrupoMuscular> findByExercicio_id(Long id, Pageable pageable);
	
	
}
