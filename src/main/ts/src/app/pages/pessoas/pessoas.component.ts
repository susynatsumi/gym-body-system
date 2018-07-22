import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Pessoa, Page } from '../../../generated/entities';
import { Router } from '../../../../node_modules/@angular/router';
import { AccountService } from '../../../generated/services';

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

  colunas: string[] = ['Nome', 'E-mail', 'Status', 'Id', 'Acoes'];
  dadosTable = new MatTableDataSource();
 pessoasTable:  Page<Pessoa>;

  constructor(
    private router: Router,
    private pessoaService: AccountService
  ){
  }

  ngOnInit() {
    this.listByfilters('');
  }

  cadastrar(){
    this.router.navigateByUrl("pessoas/cadastrar");
  }

  editar(id){
    this.router.navigate(["pessoas/", {id: id}])
  }

  delete(id){
    this.pessoaService.deletePessoa(id).subscribe(()=>{
      this.removerLinhaTable(id);
    },(error: Error)=>{
      alert('Ocorreu um erro ao deletar');
    });

  }

  removerLinhaTable(id){
    const itemIndex = this.pessoasTable.content.findIndex(pessoa => pessoa.id === id);
    this.pessoasTable.content.splice(itemIndex, 1);
    this.dadosTable = new MatTableDataSource(this.pessoasTable.content);
  }

  filtrar(filtro: string) {
    console.log(filtro);
    this.dadosTable.filter = filtro.trim().toLowerCase();
  }

  listByfilters(filters: string){
    this.pessoaService.listByFilters(filters)
      .subscribe(
        (pessoas: Page<Pessoa>) => {
          this.pessoasTable  = pessoas;
          this.dadosTable = new MatTableDataSource(pessoas.content)
        }
      );
  }

}
