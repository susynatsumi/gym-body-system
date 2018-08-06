import { Component, OnInit, Input } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, Equipamento, Exercicio } from '../../../generated/entities';
// import { Elementos } from '../../elementos/elementos';
import { ExercicioService } from '../../../generated/services';
import { Router } from '../../../../node_modules/@angular/router';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';

@Component({
  selector: 'app-exercicios',
  templateUrl: './exercicios.component.html',
  styleUrls: ['./exercicios.component.scss']
})
export class ExerciciosComponent implements OnInit {

  // tabela para apreentacao de dados
  dadosTable = new MatTableDataSource;
  // exercicios carregados
  exerciciosTable: Page<Exercicio>;

  // para apresentacao do loader
  loading = false;
  // ocultar botoes de editar, e assim por diante
  @Input() ocultarBotoes = false;

  // colunas da table
  colunas: string[] = ['nome', 'isAtivo', 'id', 'acoes'];

  /**
   * Constructor
   * @param exercicioService 
   */
  constructor(
    private exercicioService: ExercicioService,
    private router: Router,
    private mensagemService: MensagemAlertaService
  ) {
  }

  /**
   * 
   */
  ngOnInit() {
    this.listByFilters('');
  }

  /**
   * Busca dados de acordo com os filtros
   * @param filter 
   */
  listByFilters(filter: string){
    
    this.loading = true;

    this.exercicioService.listExerciciosByFilters(filter)
    .finally( () => this.loading = false )
    .subscribe( (exercicios: Page<Exercicio>) => {
      this.exerciciosTable = exercicios;
      this.dadosTable = new MatTableDataSource(this.exerciciosTable.content);
    });
    
  }

   /**
   * Remove exercicio
   * @param id 
   */
  delete(id){
    this.exercicioService.deleteExercicio(id).subscribe(()=>{
      this.removerLinhaTable(id);
    },(error: Error)=>{
      this.mensagemService.errorRemove(error.message);
    });

  }

  /**
   * Remove a linha da tabela em que foi solicitada a remoção
   * @param id 
   */
  removerLinhaTable(id){
    const itemIndex = this.exerciciosTable.content.findIndex(pessoa => pessoa.id === id);
    this.exerciciosTable.content.splice(itemIndex, 1);
    this.dadosTable = new MatTableDataSource(this.exerciciosTable.content);
  }

}
