package br.com.eits.boot.domain.entity.account;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 */
@Data
@Entity
@Audited
@Table
@EqualsAndHashCode(callSuper=true)
@DataTransferObject
public class Pessoa extends AbstractEntity implements Serializable, UserDetails
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4052986759552589018L;

	/*-------------------------------------------------------------------
	 *				 		     ATTRIBUTES
	 *-------------------------------------------------------------------*/
	// Basic
	
	/**
	 * Nome da pessoa
	 */
	@NotEmpty
	@Column(nullable = false, length = 50)
	private String nome;

	/**
	 * email, campo não é login
	 */
	@Email
	@NotNull
	@Column(nullable = false, length = 144, unique = true)
	private String email;
	
	/**
	 * login para sistema ou aplicativo, pode ser null quando a pessoa não tiver acesso
	 */
	@Column
	private String login;
	
	/**
	 * senha, poderá ser null da mesma forma que o login 
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotBlank
	@Column(length = 100)
	private String senha;

	/**
	 * objetivo da pessoa, pode ser um texto longo
	 */
	@Column(length = 255)
	private String objetivo;

	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Boolean isAtivo;


	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Papel papel;

	/**
	 * 
	 */
	private OffsetDateTime lastLogin;

	/**
	 *
	 */
	private String passwordResetToken;

	/**
	 *
	 */
	private OffsetDateTime passwordResetTokenExpiration;
	
	/*-------------------------------------------------------------------
	 *							CONSTRUCTORS
	 *-------------------------------------------------------------------*/

	/**
	 * default
	 */
	public Pessoa(){
		
	}
	
	/**
	 * 
	 * @param id
	 */
	public Pessoa( long id ){
		this.id = id;
	}

	/**
	 * 
	 * @param id
	 * @param nome
	 * @param email
	 * @param isAtivo
	 * @param papel
	 * @param senha
	 */
	public Pessoa( Long id, String nome, String email, Boolean isAtivo, Papel papel, String senha )
	{
		super( id );
		this.email = email;
		this.nome = nome;
		this.isAtivo = isAtivo;
		this.senha = senha;
		this.papel = papel;
	}
	
	/*-------------------------------------------------------------------
	 *							BEHAVIORS
	 *-------------------------------------------------------------------*/
	/**
	 * 
	 */
	@Override
	@Transient
	public Set<GrantedAuthority> getAuthorities()
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();

		if ( this.papel == null )
		{
			return null;
		}
		
		authorities.addAll( this.papel.getAuthorities() );

		return authorities;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/**
	 * 
	 */
	@Override
	@Transient
	public boolean isEnabled()
	{
		return this.isAtivo;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getPassword()
	 */
	@Override
	@Transient
	public String getPassword()
	{
		return this.senha;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	@Transient
	public String getUsername()
	{
		return this.email;
	}
	
	/*-------------------------------------------------------------------
	 *						GETTERS AND SETTERS
	 *-------------------------------------------------------------------*/
}