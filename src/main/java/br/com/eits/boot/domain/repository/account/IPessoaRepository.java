package br.com.eits.boot.domain.repository.account;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
//	Optional<Pessoa> findByPasswordResetTokenAndPasswordResetTokenExpirationAfter( String token, OffsetDateTime time );

	/**
	 *
	 */
	@Query("FROM Pessoa pessoa " +
			"WHERE "
			+ "	filter(:filter, pessoa.id, pessoa.nome, pessoa.email) = TRUE ")
	Page<Pessoa> listByFilters( 
			@Param("filter") String filter, Pageable pageable );
	
	/**
	 *
	 */
	@Query("FROM Pessoa pessoa " +
			"WHERE "+ 
			"	filter(:filter, pessoa.id, pessoa.nome, pessoa.email) = TRUE "+ 
			"	AND ( "+ 
			"			:isAtivo IS NULL "+ 
			"			OR pessoa.isAtivo = :isAtivo "+ 
			"	) AND ( "+
			"			( "+
			"				:listarAdministrador = false "+
			"				AND 0 not in elements( pessoa.papeis ) "+
			"			) "+
			"			OR ( "+
			"				:listarAdministrador = true "+
			"			) "+
			"	) AND ( "
			+ "		:somenteAlunos = false	"
			+ "		OR ( "
			+ "			:somenteAlunos = true "
			+ "			AND 2 in elements( pessoa.papeis )"
			+ "		) "
			+ "	) "
	)
	Page<Pessoa> listPessoaByFilters( 
		@Param("filter") String filter,
		@Param("isAtivo") Boolean isAtivo,
		@Param("somenteAlunos") Boolean somenteAlunos,
		@Param("listarAdministrador") Boolean listarAdministrador,
		Pageable pageable 
	);
	
	@Query("FROM Pessoa pessoa WHERE pessoa.login IS NOT NULL AND lower(pessoa.login) = lower(:login)")
	Optional<Pessoa> findByLogin( @Param("login") String login );

	@Query("FROM Pessoa pessoa "
		+ "	WHERE "
		+ "		pessoa.id = :idPessoa "
		+ "		AND ( "
		+ "				( "
		+ "					:listarAdministrador = false "
		+ "					AND 0 not in elements( pessoa.papeis ) "
		+ "				) OR ( "
		+ "					:listarAdministrador = true	"
		+ "				) "
		+ "		) "
	)
	Optional<Pessoa> findById(@Param("idPessoa") Long id, @Param("listarAdministrador") Boolean listarAdministrador);

	Optional<Pessoa> findByTokenJwt(String tokenJwt);
	
	@EntityGraph(attributePaths ={
		"avaliacoesFisicas.perimetria",
		"avaliacoesFisicas.resposta",
		"avaliacoesFisicas.avaliacaoAntropometrica.dobrasCutaneas",
		"avaliacoesFisicas.avaliacaoAntropometrica.indiceMassaCorporal"
	})
	@Query(
	  " FROM "
	+ "	Pessoa pessoa "
	+ "		JOIN pessoa.avaliacoesFisicas as avaliacaoFisica "
	+ "	WHERE "
	+ "		filter(:filter, avaliacaoFisica.id, avaliacaoFisica.pessoa.nome) = TRUE "
	+ "	AND ( "
	+ "		cast( :dataInicio as date ) is null "
	+ "		OR avaliacaoFisica.data >= :dataInicio  "
	+ "	) "
	+ "	AND ( "
	+ "		cast( :dataFim as date ) is null	"
	+ "		OR avaliacaoFisica.data <= :dataFim "
	+ "	) AND ( "
	+ "		:idPessoa is null "
	+ "		OR pessoa.id = :idPessoa "
	+ "	) "
	)
	Page<Pessoa> findPessoasComAvaliacaoFisicaByFilters(
		@Param("filter") String filter, 
		@Param("idPessoa") Long idPessoa,
		@Param("dataInicio") LocalDate dataInicio, 
		@Param("dataFim") LocalDate dataFim, 
		Pageable pageRequest	
	);
	
}
