package br.com.eits.boot.domain.entity.academia;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.directwebremoting.annotations.DataTransferObject;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

import br.com.eits.boot.domain.entity.account.Pessoa;
import br.com.eits.common.domain.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table
@Audited
@Entity
@Data
@DataTransferObject
@EqualsAndHashCode(callSuper = true)
public class Academia extends AbstractEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1907009573535423976L;

	//-----------------------------------------------
	//--------- ATRIBUTOS ---------------------------
	//-----------------------------------------------

	// razao social da academia cliente
	@NotEmpty
	@Column( nullable = false )
	private String razaoSocial;
	
	// Nome fantasia da academia cliente
	@NotEmpty
	@Column( nullable = false )
	private String nomeFantasia;
	
	// cnpj da academia cliente
	@CNPJ
	@Column( nullable = false, length = 18 , unique = true )
	private String cnpj;
	
	// telefone da academia
	@Column( nullable = false, length = 11 )
	private String telefone;
	
	// endereço da academia, rua, numero...
	@Column( nullable = false )
	private String endereco;
	
	// cep da academa
	@Column( nullable = false, length = 8 )
	private String cep;
	
	// verifica se a academia está ativa, se não estiver nenhum de seus usuários devem ter acesso
	@Column( nullable = false )
	private Boolean isAtiva;
	
	// cidade onde a academia é localizada
	@NotEmpty
	@Column( nullable = false )
	private String cidade;
	
	// Proprietario da academia
	@ManyToOne(
		cascade = CascadeType.MERGE,
		fetch = FetchType.LAZY,
		optional = false,
		targetEntity = Pessoa.class
	)
	private Pessoa pessoaProprietario;
	
	// --------------------------------------
	// ------ CONSTRUTORES ------------------
	// --------------------------------------
	
	public Academia(){
		super();
	}
	
	public Academia(Long id){
		super(id);
	}

	public Academia(
		String razaoSocial, 
		String nomeFantasia, 
		String cnpj, 
		String telefone, 
		String endereco, 
		String cep,
		Boolean isAtiva, 
		String cidade,
		Pessoa pessoaProprietario
	) {
		super();
		this.razaoSocial = razaoSocial;
		this.nomeFantasia = nomeFantasia;
		this.cnpj = cnpj;
		this.telefone = telefone;
		this.endereco = endereco;
		this.cep = cep;
		this.isAtiva = isAtiva;
		this.cidade = cidade;
		this.pessoaProprietario = pessoaProprietario;
	}
	
	
}
