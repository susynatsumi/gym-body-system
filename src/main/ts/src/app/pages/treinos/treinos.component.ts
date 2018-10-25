import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { PageRequest, Treino, Page } from '../../../generated/entities';
import { TreinoService } from '../../../generated/services';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';
import { RemoveRowTableService } from '../../services/remove-row-table.service';

import { } from 'rxjs'

@Component({
  selector: 'app-treinos',
  templateUrl: './treinos.component.html',
  styleUrls: ['./treinos.component.scss']
})
export class TreinosComponent implements OnInit {

  // tabela para apreentacao de dados
  dadosTable = new MatTableDataSource;

  // para apresentacao do loader
  loading = false;

  // colunas da table
  colunas: string[] = ['nome', 'aluno', 'id', 'acoes'];

  // para ordenar e limitar a busca
  pageRequest: PageRequest;

  constructor(
    private treinoService: TreinoService,
    private mensagemService: MensagemAlertaService,
    private removerRowTable: RemoveRowTableService,
  ) { 

    this.pageRequest = {
      page: 0,
      size: 100,
      sort: {
        orders: [
          {
            direction: 'ASC',
            nullHandlingHint: 'NATIVE',
            property: 'nome'
          },
          {
            direction: 'ASC',
            nullHandlingHint: 'NATIVE',
            property: 'aluno.nome'
          }
        ]
      }
    };

  }

  ngOnInit() {
    this.loading = true;
    this.listByFilters('');
  }

  /**
   * Faz listagem de acordo com o filtro
   * @param filter 
   */
  listByFilters(filter: string){
    this.treinoService.listTreinosByFilters(filter, this.pageRequest)
    .finally( () => this.loading = false )
    .subscribe((treinos: Page<Treino>)=>{
      this.dadosTable.data = treinos.content;
    });
    
  }

  /**
   * Remove treino do sistema, se não possuir vinculo com outras partes
   * @param id 
   */
  delete(id){

    if(!confirm('Deseja remover este treino?')) {
      return;
    }

    this.loading = true;

    this.treinoService.deleteTreino(id)
    .finally(()=> this.loading = false)
    .subscribe(()=>{
      this.dadosTable = this.removerRowTable
        .removerLinhaTable(
          id, 
          this.dadosTable.data
        );
        this.mensagemService.messageBottom('Treino removido com sucesso!');
    },(error: Error)=>{
      this.mensagemService.errorRemove(error.message);
    });

  }

}
