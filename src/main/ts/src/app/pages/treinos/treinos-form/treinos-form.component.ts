import { Component, OnInit } from '@angular/core';
import { Treino } from '../../../../generated/entities';
import { FormBuilder, FormGroup, Validators } from '../../../../../node_modules/@angular/forms';

@Component({
  selector: 'app-treinos-form',
  templateUrl: './treinos-form.component.html',
  styleUrls: ['./treinos-form.component.scss']
})
export class TreinosFormComponent implements OnInit {

  // treino populado pela tela
  treino: Treino;

  // steps do formulario
  //dados basicos
  formGrupoStep1: FormGroup;
  // dias da semana
  formGroupStep2: FormGroup;
  // exercicios do treino
  formGroupStep3: FormGroup;

  constructor(
    private formBuilder: FormBuilder
  ) { 
    this.treino = {};
  }

  ngOnInit() {

    this.formGrupoStep1 = this.formBuilder.group({
      'nome': [this.treino.nome, Validators.compose([Validators.required, Validators.minLength(1)])],
      'dataInicio': [this.treino.dataInicio, Validators.required],
      'dataFim': [this.treino.dataFim, Validators.required],
      'horaPrevistaInicio': [this.treino.horaPrevistaInicio, Validators.required],
      'horaPrevistaTermino': [this.treino.horaPrevistaTermino, Validators.required],
    });

    this.formGroupStep2 = this.formBuilder.group({
      'diasSemanaSelecionados': []
    });

    this.formGroupStep3 = this.formBuilder.group({
      'treinoExercicios': []
    });

  }

}
