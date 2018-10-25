import { Component, OnInit, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { Page, Equipamento, Exercicio, PageRequest } from '../../../generated/entities';
// import { Elementos } from '../../elementos/elementos';
import { ExercicioService } from '../../../generated/services';
import { Router } from '@angular/router';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';
import { RemoveRowTableService } from '../../services/remove-row-table.service';

@Component({
  selector: 'app-exercicios',
  templateUrl: './exercicios.component.html',
  styleUrls: ['./exercicios.component.scss']
})
export class ExerciciosComponent implements OnInit {

  // tabela para apreentacao de dados
  dadosTable = new MatTableDataSource;

  // para apresentacao do loader
  loading = false;
  // ocultar botoes de editar, e assim por diante
  @Input() ocultarBotoes = false;

  // colunas da table
  colunas: string[] = ['nome', 'isAtivo', 'id', 'acoes'];


  // para ordenar e limitar a busca
  pageRequest: PageRequest;

  /**
   * Constructor
   * @param exercicioService 
   */
  constructor(
    private exercicioService: ExercicioService,
    private router: Router,
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
          }
        ]
      }
    };

  }

  /**
   * 
   */
  ngOnInit() {
    this.loading = true;
    this.listByFilters('');
  }

  /**
   * Busca dados de acordo com os filtros
   * @param filter 
   */
  listByFilters(filter: string){
    this.exercicioService.listExerciciosByFilters(
      filter,
      null,
      this.pageRequest
    )
    .finally( () => this.loading = false )
    .subscribe( (exercicios: Page<Exercicio>) => {
      this.dadosTable.data = exercicios.content;
    });
    
  }

   /**
   * Remove exercicio
   * @param id 
   */
  delete(id){

    if(!confirm('Deseja remover este exercício?')) {
      return;
    }

    this.loading = true;

    this.exercicioService.deleteExercicio(id)
    .finally(()=> this.loading = false)
    .subscribe(()=>{
      this.dadosTable = this.removerRowTable
        .removerLinhaTable(
          id, 
          this.dadosTable.data
        );
        this.mensagemService.messageBottom('Exercicio removido com sucesso!');
    },(error: Error)=>{
      this.mensagemService.errorRemove(error.message);
    });

  }

}
