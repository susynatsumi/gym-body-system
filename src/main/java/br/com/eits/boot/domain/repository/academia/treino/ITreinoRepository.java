package br.com.eits.boot.domain.repository.academia.treino;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.treino.Treino;

public interface ITreinoRepository extends JpaRepository<Treino, Long>{


	@EntityGraph(attributePaths= {
		"treinoExercicios",
		"aluno",
		"personal"
	})
	@Override
	Optional<Treino> findById(Long id);
	
}
