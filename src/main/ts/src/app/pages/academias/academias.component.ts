import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { Page, Academia, PageRequest } from '../../../generated/entities';
import { Router } from '@angular/router';
import { AcademiaService } from '../../../generated/services';

import 'rxjs/add/operator/finally';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';
import { RemoveRowTableService } from '../../services/remove-row-table.service';

@Component({
  selector: 'app-academias',
  templateUrl: './academias.component.html',
  styleUrls: ['./academias.component.scss']
})
export class AcademiasComponent implements OnInit {

  // ---------------------------------------------
  // ---------------- ATRIBUTOS ------------------
  // ---------------------------------------------
  // colunas da tabela
  colunas: string[] = ['razaoSocial', 'cidade', 'telefone', 'id', 'acoes'];
  // data source da table
  dadosTable = new MatTableDataSource();
  academiasTable:  Page<Academia>;

  loading = false;

  pageRequest: PageRequest;

  // ---------------------------------------------
  // ---------------- CONSTRUCTOR ----------------
  // ---------------------------------------------

  /**
   * 
   * @param academiaService 
   * @param mensagemService 
   * @param removeRow 
   */
  constructor(
    private academiaService: AcademiaService,
    private mensagemService: MensagemAlertaService, 
    private removeRow: RemoveRowTableService
  ) { 

    this.pageRequest = {
      page: 0,
      size: 500,
      sort: {
        orders: [
          {
            direction: 'ASC',
            nullHandlingHint: 'NATIVE',
            property: 'razaoSocial'
          }
        ]
      }
    }

  }

  // ---------------------------------------------
  // ---------------- MÉTODOS   ------------------
  // ---------------------------------------------

  /**
   * 
   */
  ngOnInit() {
    this.loading = true;
    this.listByfilters('');
  }

  /**
   * Faz requisição ao servidor de acordo com o filtro
   * @param filters 
   */
  listByfilters(filters: string){
    this.academiaService.listAcademiaByFilters(filters, this.pageRequest)
      .finally( () => this.loading = false )
      .subscribe(
        (academias: Page<Academia>) => {
          // this.academiasTable  = academias;
          this.dadosTable.data = academias.content;
        },(error: Error)=>{
          this.mensagemService.message(error.message);
        }
      );
  }

  /**
   * Delete academia de acordo com o id
   * @param id 
   */
  delete(id){

    this.loading = true;
    
    this.academiaService.deleteAcademia(id)
    .finally(()=> this.loading = false)
    .subscribe(()=>{
      this.dadosTable = this.removeRow
        .removerLinhaTable(
          id, 
          this.dadosTable.data
      );

      this.mensagemService.messageBottom('Academia removida com sucesso!');

    },(error: Error)=>{

      this.mensagemService.errorRemove(error.message);

    });

  }

}
