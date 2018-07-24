import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Pessoa, Page } from '../../../generated/entities';
import { Router } from '../../../../node_modules/@angular/router';
import { AccountService } from '../../../generated/services';

import 'rxjs/add/operator/finally';

// const pessoas: Pessoa[] = [
//   {nome: 'Teste', email: 'teste@email', isAtivo: true, id: 1},
//   {nome: 'Ursinho da Susy', email: 'teste2@email', isAtivo: false, id: 2}
// ];

/**
 * @title Basic use of `<mat-table>` (uses display flex)
 */
@Component({
  selector: 'app-pessoas',
  templateUrl: './pessoas.component.html',
  styleUrls: ['./pessoas.component.scss']
})
export class PessoasComponent implements OnInit {

  // ---------------------------------------------
  // ----------------- ATRIBUTOS -----------------
  // ---------------------------------------------

  colunas: string[] = ['Nome', 'E-mail', 'Status', 'Id', 'Acoes'];
  dadosTable = new MatTableDataSource();
  pessoasTable:  Page<Pessoa>;
  loading = false;


  // ---------------------------------------------
  // ----------------- CONSTRUCTOR ---------------
  // ---------------------------------------------

  constructor(
    private router: Router,
    private pessoaService: AccountService
  ){
  }

  // ---------------------------------------------
  // ----------------- MÉTODOS -------------------
  // ---------------------------------------------

  ngOnInit() {
    this.listByfilters('');
  }

  /**
   * Redireciona para tela de cadastro de pessoas
   */
  cadastrar(){
    this.router.navigateByUrl("pessoas/cadastrar");
  }

  /**
   * Redireciona para a tela de edição de pessoas
   * @param id
   */
  editar(id){
    this.router.navigate(["pessoas/", {id: id}])
  }

  /**
   * Remove pessoa
   * @param id 
   */
  delete(id){
    this.pessoaService.deletePessoa(id).subscribe(()=>{
      this.removerLinhaTable(id);
    },(error: Error)=>{
      alert('Ocorreu um erro ao deletar');
    });

  }

  /**
   * Remove a linha da tabela em que foi solicitada a remoção
   * @param id 
   */
  removerLinhaTable(id){
    const itemIndex = this.pessoasTable.content.findIndex(pessoa => pessoa.id === id);
    this.pessoasTable.content.splice(itemIndex, 1);
    this.dadosTable = new MatTableDataSource(this.pessoasTable.content);
  }

  /**
   * Filtra os dados já caregados na table
   * @param filtro
   */
  filtrar(filtro: string) {
    console.log(filtro);
    this.dadosTable.filter = filtro.trim().toLowerCase();
  }

  /**
   * Faz uma busca no servidor de acordo com o filtro
   * @param filters 
   */
  listByfilters(filters: string){
    this.loading= true;
    this.pessoaService.listByFilters(filters)
    .finally(() => this.loading = false )
      .subscribe(
        (pessoas: Page<Pessoa>) => {
          this.pessoasTable  = pessoas;
          this.dadosTable = new MatTableDataSource(pessoas.content)
        }
      );
  }

}
