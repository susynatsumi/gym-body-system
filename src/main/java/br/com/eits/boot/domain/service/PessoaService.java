package br.com.eits.boot.domain.service;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;

/**
 * 
 * @author Eduardo
 *
 */
@Service
@RemoteProxy
@Transactional
public class PessoaService {

	@Autowired
	private IPessoaRepository pessoaRepository;
	
	/**
	 * 
	 * Busca pessoa através do campo login
	 * 
	 * @param login
	 * @return Pessoa
	 */
	public Pessoa findPessoaByLogin( String login ){
		
		Assert.hasLength(login, "service.pessoa.login.is.empty");
		
		return this.pessoaRepository.findByLogin(login).orElse(null);
		
	}
	
	public List<Pessoa> listarTodos() {
		return this.pessoaRepository.findAll();
	}
	
}
