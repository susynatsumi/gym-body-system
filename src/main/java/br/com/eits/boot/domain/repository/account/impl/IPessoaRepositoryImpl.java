package br.com.eits.boot.domain.repository.account.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
	@Override
	@Transactional
	public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException
	{
		try
		{
			final String hql = "FROM Pessoa pessoa "
							+ "WHERE "
							+ "		pessoa.login is not null "
							+ "		AND pessoa.login = :login "
							+ "		AND pessoa.isAtivo = true" // somente ativos podem logar
							+ "		AND pessoa.papeis not in ( 2 ) "; // alunos não podem logar no sistema
			
			final TypedQuery<Pessoa> query = this.entityManager.createQuery( hql, Pessoa.class );
			query.setParameter( "login", login);
			
			return query.getSingleResult();
		}
		catch (NoResultException e)
		{
			throw new UsernameNotFoundException("Este login: '"+login+"' é inválido");
		}
	}
}
