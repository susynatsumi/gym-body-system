package br.com.eits.boot.domain.repository.academia.notificacao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.notificacao.PessoaNotificacao;

public interface IPessoaNotificacaoRepository extends JpaRepository<PessoaNotificacao, Long>{

}
