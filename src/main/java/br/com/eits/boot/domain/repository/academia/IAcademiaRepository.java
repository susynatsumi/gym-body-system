package br.com.eits.boot.domain.repository.academia;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.Academia;

public interface IAcademiaRepository extends JpaRepository<Academia, Long>{

}
