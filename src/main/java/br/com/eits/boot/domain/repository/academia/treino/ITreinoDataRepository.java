package br.com.eits.boot.domain.repository.academia.treino;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.treino.TreinoData;

public interface ITreinoDataRepository extends JpaRepository<TreinoData, Long>{

}
