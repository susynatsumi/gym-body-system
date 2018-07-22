import { Component } from '@angular/core';
import { AccountService } from '../generated/services';
import { Pessoa } from '../generated/entities';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(
    private pessoaService: AccountService
  ){
    
    pessoaService.getPessoaLogada().subscribe((usuario: Pessoa)=>{
      if(usuario != null){
        console.log('Logado '+usuario.nome);
        console.log('Papel '+usuario.papel);
        localStorage.setItem('usuarioLogado', JSON.stringify(usuario));
      }
    });

  }

}
