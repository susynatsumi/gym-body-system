import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '../../../../../node_modules/@angular/forms';
import { TreinoExercicio, Treino, Page, Exercicio } from '../../../../generated/entities';
import { TreinoExercicioService, ExercicioService } from '../../../../generated/services';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';

@Component({
  selector: 'app-treino-exercicio-form',
  templateUrl: './treino-exercicio-form.component.html',
  styleUrls: ['./treino-exercicio-form.component.scss']
})
export class TreinoExercicioFormComponent implements OnInit {


  // -------------------------------------
  // ------------ ATRIBUTOS --------------
  // -------------------------------------

  // objeto populado na tela
  treinoExercicio: TreinoExercicio;

  // form group para vaidação dos campos
  formGroupTreinoExercicio: FormGroup;

  // exercicios para popular o auto complete
  exerciciosAutoComplete: Treino[];

  /**
   * 
   */
  constructor(
    private formBuilder: FormBuilder,
    private exercicioService: ExercicioService,
    private mensagemAlertaService: MensagemAlertaService
  ) { 
    this.treinoExercicio = {
      exercicio: {nome: ''},
      isAtivo: true,
    };
  }

  /**
   * Inicialização do form
   */
  ngOnInit() {

    this.formGroupTreinoExercicio = this.formBuilder.group({
      'exercicio': [this.treinoExercicio.exercicio, Validators.required],
      'tipoTreinoExercicio': [this.treinoExercicio.tipoTreinoExercicio, Validators.required],
      'carga': [this.treinoExercicio.carga],
      'repeticoes': [this.treinoExercicio.repeticoes],
      'tempoMin': [this.treinoExercicio.tempoMin],
      'observacoes': [this.treinoExercicio.observacoes]
    });

  }

  /**
   * Busca os exercicios para o autocomplete
   * @param filtro 
   */
  listTreinosByFilters( filtro: string ){

    this.exercicioService.listExerciciosByFilters(filtro)
      .subscribe((exercicios: Page<Exercicio>) =>{
        this.exerciciosAutoComplete = exercicios.content;
      }, (error: Error)=>{
        this.mensagemAlertaService.errorFind(error.message);
      });
  }

  setExercicio(treinoExercicio, exercicio){

    if(treinoExercicio){
      treinoExercicio.exercicio = exercicio;
    }

  }

}
