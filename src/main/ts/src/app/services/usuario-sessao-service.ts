import { Injectable } from "../../../node_modules/@angular/core";
import { AccountService } from "../../generated/services";
import { Pessoa } from "../../generated/entities";

@Injectable()
export class UsuarioSessaoService {
    
  private usuario;

  constructor(
    private pessoaService: AccountService
  ){
    
  }

  findPessoa(){
    this.pessoaService.getPessoaLogada().subscribe((usuario: Pessoa)=>{
        if(usuario != null){
          console.log('Logado '+usuario.nome);
          console.log('Papel '+usuario.papeis);
          this.usuario = usuario;
        }
      });
  }

  get usuarioLogado(){
    return this.usuario;
  }

  get isAdministrador(){

    if(this.usuarioLogado == null){
        return false;
    }

    let papel = this.usuarioLogado.papeis
    .find(
      ad =>
        ad.toString() == 'ADMINISTRATOR'
    );

    return papel != null; 
  }
}