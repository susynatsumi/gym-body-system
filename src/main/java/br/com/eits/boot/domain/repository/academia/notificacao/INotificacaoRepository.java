package br.com.eits.boot.domain.repository.academia.notificacao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eits.boot.domain.entity.academia.notificacao.Notificacao;

public interface INotificacaoRepository extends JpaRepository<Notificacao, Long>{

}
