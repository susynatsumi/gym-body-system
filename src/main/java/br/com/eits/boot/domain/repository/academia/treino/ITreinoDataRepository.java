package br.com.eits.boot.domain.repository.academia.treino;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.treino.TreinoData;

public interface ITreinoDataRepository extends JpaRepository<TreinoData, Long>{

	List<TreinoData> findByTreino_Id( Long id );
	
}
