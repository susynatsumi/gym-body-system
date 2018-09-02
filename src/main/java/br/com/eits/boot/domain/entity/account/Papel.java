package br.com.eits.boot.domain.entity.account;

import org.directwebremoting.annotations.DataTransferObject;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 */
@DataTransferObject(type = "enum")
public enum Papel implements GrantedAuthority
{
	/*-------------------------------------------------------------------
	 *				 		     ENUMS
	 *-------------------------------------------------------------------*/
	ADMINISTRATOR, // 0
	PERSONAL, // 1
	ALUNO; // 2

	public static final String ADMINISTRATOR_VALUE 	= "ADMINISTRATOR";
	public static final String PERSONAL_VALUE 		= "PERSONAL";
	public static final String ALUNO_VALUE 			= "ALUNO";
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority()
	{
		return this.name();
	}

	public GrantedAuthority getGrantedAuthority(){
		return this;
	}
	
	/**
	 * @return
	 *//*
	public Set<GrantedAuthority> getAuthorities()
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();

		authorities.add( this );

		if ( this.equals( Papel.ADMINISTRATOR ) )
		{
			authorities.add( Papel.PERSONAL );
		}

		authorities.add( Papel.ALUNO );

		return authorities;
	}*/
}