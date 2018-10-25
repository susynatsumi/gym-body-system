import { Component, OnInit, Output, Input, EventEmitter, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, PageEvent, MatPaginatorIntl } from '@angular/material';
import { Pessoa, Page, PageRequest } from '../../../generated/entities';
import { Router } from '@angular/router';
import { AccountService } from '../../../generated/services';

import 'rxjs/add/operator/finally';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';
import { RemoveRowTableService } from '../../services/remove-row-table.service';

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
  // resposta para a dialog de pessoas
  @Output() respostaDialog = new EventEmitter();
  // oculta alguns botoes se estiver com valor true
  @Input() ocultarBotoes: Boolean = false;

  // colunas da table
  colunas: string[] = ['Nome', 'E-mail', 'Status', 'Id', 'Acoes'];
  // datasource da table
  dadosTable = new MatTableDataSource<Pessoa>();

  // para apresentar ou não o loader
  loading = false;

  // para ordenar e limitar a busca
  pageRequest: PageRequest;

  // ---------------------------------------------
  // ----------------- CONSTRUCTOR ---------------
  // ---------------------------------------------

  /**
   * 
   * @param router 
   * @param pessoaService 
   * @param mensagemAlertaService 
   * @param removerRowService 
   */
  constructor(
    private router: Router,
    private pessoaService: AccountService,
    private mensagemAlertaService: MensagemAlertaService,
    private removerRowService: RemoveRowTableService
  ){

    this.pageRequest = {
      page: 0,
      size: 100,
      sort: {
        orders: [{
          property: 'nome',
          direction: 'ASC',
          nullHandlingHint: 'NATIVE'
          },
          {
            property: 'isAtivo',
            direction: 'DESC',
            nullHandlingHint: 'NATIVE'
          }
        ]
      }
    };
  }

  // ---------------------------------------------
  // ----------------- MÉTODOS -------------------
  // ---------------------------------------------

  /**
   * 
   */
  ngOnInit() {
    this.loading= true;
    this.listByfilters('');
  }

  /**
   * Redireciona para tela de cadastro de pessoas
   */
  cadastrar(){
    this.router.navigateByUrl("pessoas/cadastrar");
  }

  /**
   * Remove pessoa
   * @param id 
   */
  delete(id){
    
    if(!confirm('Deseja remover esta pessoa?')) {
      return;
    }

    this.loading = true;
    
    this.pessoaService.deletePessoa(id)
    .finally( () => this.loading = false ) 
    .subscribe(()=>{

      this.dadosTable = this.removerRowService
        .removerLinhaTable(id, this.dadosTable.data);

        this.mensagemAlertaService
          .messageBottom('Pessoa removida com sucesso!');

    },(error: Error)=>{

      this.mensagemAlertaService.errorRemove(error.message);

    });

  }

  /**
   * Faz uma busca no servidor de acordo com o filtro
   * @param filters 
   */
  listByfilters(filters: string){

    this.pessoaService.listByFilters(filters, null , false, this.pageRequest)
    .finally(() => this.loading = false )
      .subscribe(
        (pessoas: Page<Pessoa>) => {
          this.dadosTable.data = pessoas.content;
        }
      );
  }

  /**
   * Seleciona pessoa da dialog
   * @param element 
   */
  selecionar(element){
    this.respostaDialog.emit(element);
  }

}
