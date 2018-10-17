package br.com.eits.boot.domain.service;

import static br.com.eits.common.application.i18n.MessageSourceHolder.translate;

import java.time.LocalDate;

import org.directwebremoting.annotations.RemoteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import br.com.eits.boot.application.security.RequestContext;
import br.com.eits.boot.domain.entity.account.Papel;
import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.boot.domain.repository.account.IPessoaRepository;
import br.com.eits.common.application.i18n.MessageSourceHolder;

/**
 * @author rodrigo@eits.com.br
 */
@Service
@RemoteProxy
@Transactional
public class AccountService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * Password encoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	//Repositories
	/**
	 *
	 */
	@Autowired
	private IPessoaRepository pessoaRepository;

//	@Autowired
//	private IAccountMailRepository accountMailRepository;

	/*-------------------------------------------------------------------
	 *				 		     SERVICES
	 *-------------------------------------------------------------------*/

	/**
	 * @param user
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "')")
	public Pessoa insertPessoa( Pessoa user)
	{
		user.setIsAtivo( true );
		user.setSenha (user.getLogin() );
		user.setSenha( this.passwordEncoder.encode( (user.getPassword() == null? "" : user.getPassword() ) ) );

		user = this.pessoaRepository.save( user );
//		this.accountMailRepository.sendNewUserAccount( user );
		return user;
	}

	// aluno tambem pode fazer login pelo app
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	public Pessoa updatePessoa(Pessoa pessoa){
		
		Assert.notNull(pessoa, "pessoa.service.pessoa.null");
		Assert.notNull(pessoa.getId(), "pessoa.service.pessoa.id.null");
		
		return this.pessoaRepository.save(pessoa);
	}
	
	
	/**
	 * 
	 * Altera senha do usuário
	 * 
	 * @param idPessoa id da pessoa que deseja alterar a senha
	 * @param novaSenha string descriptografada
	 * @param senhaAntiga senha descriptografada
	 */
	public void alterarSenha(long idPessoa, String novaSenha, String senhaAntiga) {
		
		Pessoa pessoa = this.findPessoaById(idPessoa);
		
		Assert.isTrue(
			this.passwordEncoder.matches(senhaAntiga, pessoa.getSenha()),
			MessageSourceHolder.translate("userService.alterar.senha.nao.confere")
		);
		
		pessoa.setSenha( this.passwordEncoder.encode( novaSenha ) );
		
		this.pessoaRepository.save(pessoa);
		
	}
	
	/**
	 * @param id
	 * @return
	 */
	@Transactional( readOnly = true )
	public Pessoa findPessoaById( long id ) {
		
		final Pessoa pessoaSession = this.getPessoaLogada();
		
		return this.pessoaRepository.findById( 
			id ,
			pessoaSession != null 
				&& pessoaSession.getPapeis().contains(Papel.ADMINISTRATOR)
		)
		.orElseThrow( 
			() -> new IllegalArgumentException( 
				translate( "repository.notFoundById", id ) 
			) 
		);
		
	}

	/**
	 * 
	 * @param filtro
	 * @param isAtivo
	 * @param somenteAlunos
	 * @param pageRequest
	 * @return
	 */
	@Transactional( readOnly = true )
	public Page<Pessoa> listByFilters(
		String filtro,
		Boolean isAtivo,
		Boolean somenteAlunos,
		PageRequest pageRequest 
	){
		final Pessoa pessoaSession = this.getPessoaLogada();
		return this.pessoaRepository.listPessoaByFilters(
			filtro, 
			isAtivo, 
			somenteAlunos,
			pessoaSession != null 
				&& pessoaSession.getPapeis().contains(Papel.ADMINISTRATOR),// verifica se pode ou não listar administradores 
			pageRequest
		);
	}
	
	/**
	 * @param pageable
	 * @param filter
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Pessoa> listUsersByFilters( String filter, PageRequest pageable )
	{
		return this.pessoaRepository.listByFilters( filter, pageable );
	}
	
	/**
	 * Retorna pessoa logada na web
	 * @return
	 */
	public Pessoa getPessoaLogada(){
		Pessoa pessoaLogada = RequestContext.currentUser().orElse(null); 
		return pessoaLogada;
	}
	
	/**
	 * Faz delete da pessoa com o id informado
	 * @param id
	 */
	public void deletePessoa( Long id ){
		this.pessoaRepository.deleteById(id);
	}
	
	/**
	 * 
	 * Busca uma pessoa de acordo com o token jwt 
	 * 
	 * @param tokenJwt
	 * @return
	 */
	public Pessoa findPessoaByToken(String tokenJwt){
		return this.pessoaRepository.findByTokenJwt(tokenJwt)
		.orElseThrow(()->
			new IllegalArgumentException(
				MessageSourceHolder.translate(
					"userService.token.invalido"
				)
			)
		);
	}
	
	/**
	 * Lista avaliações fisicas pelos fitros, nome treino, id pessoa, nome pessoa
	 * Alunos não podem acessar este método, pois não podem ver avaliacoes de outras pessoas, somente as suas
	 * @param filters
	 * @param pageRequest
	 * @return
	 */
	@PreAuthorize("hasAnyAuthority('" + Papel.ADMINISTRATOR_VALUE + "','" + Papel.PERSONAL_VALUE + "','" + Papel.ALUNO_VALUE + "')")
	@Transactional( readOnly = true )
	public Page<Pessoa> listAvaliacaoFisicaByFilters(
		String filters,
		Long idPessoa,
		LocalDate dataInicio, 
		LocalDate dataFim, 
		PageRequest pageRequest
	){
		
		return this.pessoaRepository
				.findPessoasComAvaliacaoFisicaByFilters(
					filters, 
					idPessoa, 
					dataInicio, 
					dataFim, 
					pageRequest
				);
		
	}
	
}