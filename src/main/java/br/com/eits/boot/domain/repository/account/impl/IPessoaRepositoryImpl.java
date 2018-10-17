package br.com.eits.boot.domain.repository.account.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.eits.boot.domain.entity.account.Pessoa;

/**
 *
 */
public class IPessoaRepositoryImpl implements UserDetailsService
{
	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	private final EntityManager entityManager;

	@Autowired
	public IPessoaRepositoryImpl( EntityManager entityManager )
	{
		this.entityManager = entityManager;
	}

	/*-------------------------------------------------------------------
	 *				 		     BEHAVIORS
	 *-------------------------------------------------------------------*/
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@JsonIgnoreProperties(value = {"avaliacoesFisicas"})
	@Override
	@Transactional( readOnly = true )
	public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException
	{
		try
		{
			final String hql = "FROM Pessoa pessoa "
					+ "WHERE "
					+ "		pessoa.login is not null "
					+ "		AND pessoa.login = :login "
					+ "		AND pessoa.isAtivo = true "; // somente ativos podem logar
//					+ "		AND ( "
//					+ "				exists( select 1 from pessoa.papeis papel where papel <> :papelAluno ) "
//					+ "		) "; // alunos não podem logar no sistema
			
//			1 in elements(pessoa.papeis) "
			
			final TypedQuery<Pessoa> query = this.entityManager.createQuery( hql, Pessoa.class );
			query.setParameter( "login", login );
//			query.setParameter( "papelAluno", Papel.ALUNO);
			
			UserDetails u = query.getSingleResult();
			return u;
		}
		catch (NoResultException e)
		{
			e.printStackTrace();
			throw new UsernameNotFoundException("Este login: '"+login+"' é inválido");
		}
	}
}
