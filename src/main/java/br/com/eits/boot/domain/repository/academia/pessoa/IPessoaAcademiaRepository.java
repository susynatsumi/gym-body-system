package br.com.eits.boot.domain.repository.academia.pessoa;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.pessoa.PessoaAcademia;

public interface IPessoaAcademiaRepository extends JpaRepository<PessoaAcademia, Long>{

}
