import { Component, OnInit, Input, Output, EventEmitter, OnChanges, SimpleChange, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TreinoExercicio, Treino, Page, Exercicio, TipoTreinoExercicioValues, TipoTreinoExercicio } from '../../../../generated/entities';
import { ExercicioService } from '../../../../generated/services';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';

@Component({
  selector: 'app-treino-exercicio-form',
  templateUrl: './treino-exercicio-form.component.html',
  styleUrls: ['./treino-exercicio-form.component.scss']
})
export class TreinoExercicioFormComponent implements OnInit, OnChanges {


  // -------------------------------------
  // ------------ ATRIBUTOS --------------
  // -------------------------------------

  // objeto populado na tela
  @Input() treinoExercicio: TreinoExercicio;

  @Output() apagarTreinoExercicio = new EventEmitter();
  @Output() treinoExercicioAtual = new EventEmitter();

  // form group para vaidação dos campos
  @Input() formGroupTreinoExercicio: FormGroup;

  // index deste item no form array
  @Input() indexDesteItem: number ;
  // item aberto do expansion panel
  @Input() itemAtual: number;

  // exercicios para popular o auto complete
  exerciciosAutoComplete: Treino[];

  // tipos de treino exercicio para o select
  tiposTreinoExercicio: string[];

  
  /**
   * 
   * @param formBuilder 
   * @param exercicioService 
   * @param mensagemAlertaService 
   */
  constructor(
    private exercicioService: ExercicioService,
    private mensagemAlertaService: MensagemAlertaService
  ) { 

    this.tiposTreinoExercicio = TipoTreinoExercicioValues;
    
  }

  /**
   * 
   */
  ngOnInit() {
    console.log(this.treinoExercicio);
  }

  /**
   * Captura as alterações feitas pelos atributos anotados com @input
   * @param changes 
   */
  ngOnChanges(changes: SimpleChanges) {
    this.itemAtual = changes.itemAtual.currentValue;
  }

  /**
   * Busca os exercicios para o autocomplete
   * @param filtro 
   */
  listExerciciosByFilters( filtro: string ){

    this.exercicioService.listExerciciosByFilters(filtro)
      .subscribe((exercicios: Page<Exercicio>) =>{
        this.exerciciosAutoComplete = exercicios.content;
      }, (error: Error)=>{
        this.mensagemAlertaService.errorFind(error.message);
      });
  }

  /**
   * Seta o exercicio do autocomplete
   * @param treinoExercicio 
   * @param exercicio 
   */
  setExercicio(treinoExercicio, exercicio){

    if(treinoExercicio){
      treinoExercicio.exercicio = exercicio;
    }

  }

  /**
   * Remove da table ou inativa um exercicio do treino 
   * @param element 
   */
  delete(element: TreinoExercicio){
    this.apagarTreinoExercicio.emit(element);
  }

  /**
   * Emite evendo para o componente pai, solicitando a mudanca do index selecionado 
   * expandindo somente este expansion panel
   * @param index 
   */
  setItemAtual(index){
    this.treinoExercicioAtual.emit(index);
  }

  /**
   * Altera os campos obrigatórios de acordo com o tipo treino exercicio selecionado
   * @param tipoSelecionado 
   */
  // change***
  chageTipoTreinoExercicio(tipoSelecionado){

    this.formGroupTreinoExercicio.controls['carga'].clearValidators();
    this.formGroupTreinoExercicio.controls['repeticoes'].clearValidators();
    this.formGroupTreinoExercicio.controls['tempoMin'].clearValidators();

    if(tipoSelecionado === 'CARGA_REPETICOES'){
      this.formGroupTreinoExercicio.controls['carga'].setValidators([Validators.required]);
      this.formGroupTreinoExercicio.controls['repeticoes'].setValidators([Validators.required]);
    } else if(tipoSelecionado === 'REPETICOES' ){
      this.formGroupTreinoExercicio.controls['repeticoes'].setValidators([Validators.required]);
    } else {
      this.formGroupTreinoExercicio.controls['tempoMin'].setValidators([Validators.required]);
    }

    console.log(this.formGroupTreinoExercicio.controls);
    console.log(this.formGroupTreinoExercicio.invalid);
    console.log(this.formGroupTreinoExercicio.touched);

  }

}
