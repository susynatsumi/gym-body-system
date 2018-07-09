package br.com.eits.boot.domain.repository.account;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.eits.boot.domain.entity.account.Pessoa;

/**
 * 
 */
public interface IPessoaRepository extends JpaRepository<Pessoa, Long>
{
	/**
	 *
	 */
	@Query("FROM Pessoa pessoa WHERE lower(pessoa.email) = lower(:email)")
	Optional<Pessoa> findByEmail( @Param("email") String email );

	/**
	 *
	 */
	Optional<Pessoa> findByPasswordResetTokenAndPasswordResetTokenExpirationAfter( String token, OffsetDateTime time );

	/**
	 *
	 */
	@Query("FROM Pessoa pessoa " +
			"WHERE filter(:filter, pessoa.id, pessoa.nome, pessoa.email) = TRUE ")
	Page<Pessoa> listByFilters( @Param("filter") String filter, Pageable pageable );
	
	@Query("FROM Pessoa pessoa WHERE pessoa.login IS NOT NULL AND lower(pessoa.login) = lower(:login)")
	Optional<Pessoa> findByLogin( @Param("login") String login );
	
	
}
