import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TreinoExercicio, Treino, Page, Exercicio, TipoTreinoExercicioValues } from '../../../../generated/entities';
import { ExercicioService } from '../../../../generated/services';
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
  @Input() treinoExercicio: TreinoExercicio;

  @Output() apagarTreinoExercicio = new EventEmitter();

  // form group para vaidação dos campos
  formGroupTreinoExercicio: FormGroup;

  // exercicios para popular o auto complete
  exerciciosAutoComplete: Treino[];

  tiposTreinoExercicio: string[];

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
      tipoTreinoExercicio: 'CARGA_REPETICOES',
    };

    this.tiposTreinoExercicio = TipoTreinoExercicioValues;
    
  }

  /**
   * Inicialização do form
   */
  ngOnInit() {

    this.formGroupTreinoExercicio = this.formBuilder.group({
      'exercicio': [
        this.treinoExercicio.exercicio, 
        Validators.required
      ],
      'tipoTreinoExercicio': [
        this.treinoExercicio.tipoTreinoExercicio, 
        Validators.required
      ],
      'series': [
        this.treinoExercicio.series, 
        Validators.compose([
          Validators.required, 
          Validators.min(0), 
          Validators.max(9) 
        ])
      ],
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

 /*  setTipoTreinoExercicio(treinoExercicio){
    if( treinoExercicio ){
      treinoExercicio.tipoTreinoExercicio = this.tiposTreinoExercicio;
    }
  } */

  /**
   * Remove da table ou inativa um exercicio do treino 
   * @param element 
   */
  delete(element: TreinoExercicio){
    this.apagarTreinoExercicio.emit(element);
  }


}
