import { Component, OnInit } from '@angular/core';

import 'rxjs/add/operator/finally';
import { Exercicio, ExercicioGrupoMuscular } from '../../../../generated/entities';
import { ExercicioService } from '../../../../generated/services';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';
import { Router, ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-exercicios-detalhar',
  templateUrl: './exercicios-detalhar.component.html',
  styleUrls: ['./exercicios-detalhar.component.scss']
})
export class ExerciciosDetalharComponent implements OnInit {

  // -----------------------------------
  // -------------- ATRIBUTOS ----------
  // -----------------------------------

  // colunas
  colunas: string[] = ['nome', 'id'];
  // data source apresentado na table
  dadosTable: MatTableDataSource<ExercicioGrupoMuscular>;

  // exercicio apresentado na tela
  exercicio: Exercicio;

  // parametro passado na url
  parametroId: number;

  // apresetar ou não o loader na tela
  loading = true;

  // -----------------------------------
  // ----- CONSTRUCTOR -----------------
  // -----------------------------------

  constructor(
    private exercicioService: ExercicioService,
    private mensagemService: MensagemAlertaService,
    private router: Router,
    private route: ActivatedRoute,
  ) { 

    this.route.params.subscribe(
      paramns => {
        this.parametroId = paramns.id;

        this.exercicioService.findExercicioById(this.parametroId)
          .finally( () => this.loading = false )
          .subscribe(
            (exercicio: Exercicio) =>{
              this.exercicio = exercicio;

              this.dadosTable = new MatTableDataSource(this.exercicio.exercicioGrupoMusculares);
            },(error: Error)=>{
              this.mensagemService.errorFind(error.message);
              router.navigate(['exercicios']);
            }
          );
      });
  }

  /**
   * 
   */
  ngOnInit() {
  }

  /**
   * Redireciona para a página de edição
   * @param id 
   */
  editar(id){
    this.router.navigate(['exercicios/editar',id]);
  }

}
