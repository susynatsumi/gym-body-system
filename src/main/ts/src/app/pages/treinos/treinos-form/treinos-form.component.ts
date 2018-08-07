import { Component, OnInit } from '@angular/core';
import { Treino, Pessoa } from '../../../../generated/entities';
import { FormBuilder, FormGroup, Validators, ValidationErrors } from '../../../../../node_modules/@angular/forms';
import { PessoaDialogComponent } from '../../dialogs/pessoa-dialog/pessoa-dialog.component';
import { MatDialog } from '../../../../../node_modules/@angular/material';

@Component({
  selector: 'app-treinos-form',
  templateUrl: './treinos-form.component.html',
  styleUrls: ['./treinos-form.component.scss']
})
export class TreinosFormComponent implements OnInit {

  // data atual para data minima para selecionar
  minDate = new Date();

  // treino populado pela tela
  treino: Treino;
  alunoSelecionado: Pessoa[];

  // steps do formulario
  //selecionar aluno
  formGrupoStep1: FormGroup;
  // dados basicos
  formGrupoStep2: FormGroup;
  // dias da semana
  formGroupStep3: FormGroup;
  // exercicios do treino
  formGroupStep4: FormGroup;

  // dias da semana para check box
  segunda: false;
  terca: false;
  quarta: false;
  quinta: false;
  sexta: false;
  sabado: false;
  domingo: false;

  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
  ) { 
    this.treino = {};
  }

  ngOnInit() {

    this.formGrupoStep1 = this.formBuilder.group({
      'alunoSelecionado': [this.alunoSelecionado, Validators.required]
    });

    this.formGrupoStep2 = this.formBuilder.group({
      'nome': [this.treino.nome, Validators.compose([Validators.required, Validators.minLength(1)])],
      'dataInicio': [this.treino.dataInicio, Validators.required],
      'dataFim': [this.treino.dataFim, Validators.required],
      'horaPrevistaInicio': [this.treino.horaPrevistaInicio, Validators.required],
      'horaPrevistaTermino': [this.treino.horaPrevistaTermino, Validators.required],
    });

    this.formGroupStep3 = this.formBuilder.group({
      'segunda': [],
      'terca': [],
      'quarta': [],
      'quinta': [],
      'sexta': [],
      'sabado': [],
      'domingo': [],
    });

    this.formGroupStep4 = this.formBuilder.group({
      'treinoExercicios': []
    });

  }

  abrirDialog() {
    const dialogRef = this.dialog.open(PessoaDialogComponent, {
      width: '80%',
      height: '90%',
      data: {}
    });

    dialogRef.afterClosed().subscribe((pessoaSelecionada:Pessoa) => {
      this.formGrupoStep1.setValue({
        'alunoSelecionado': pessoaSelecionada,
      },
      {
        emitEvent: true, 
        onlySelf: false
      });

    });
  }

  selecionarDiasSemana(){

    this.treino.diasSemanaSelecionados = [];

    if( this.segunda ){
      this.treino.diasSemanaSelecionados.push('SEGUNDA');
    }
    if( this.terca ){
      this.treino.diasSemanaSelecionados.push('TERCA');
    }
    if( this.quarta ){
      this.treino.diasSemanaSelecionados.push('QUARTA');
    }
    if( this.quinta ){
      this.treino.diasSemanaSelecionados.push('QUINTA');
    }
    if( this.sexta ){
      this.treino.diasSemanaSelecionados.push('SEXTA');
    }
    if( this.sabado ){
      this.treino.diasSemanaSelecionados.push('SABADO');
    }
    if( this.domingo ){
      this.treino.diasSemanaSelecionados.push('DOMINGO');
    }

    if(this.treino.diasSemanaSelecionados.length == 0){
      this.formGroupStep3.controls['segunda'].setErrors({'invalido':true});
    }
    console.log(this.treino.diasSemanaSelecionados.length);
  }

  salvar(){
    console.log('hora inicio '+ this.treino.horaPrevistaInicio);
    console.log('hora Fim '+ this.treino.horaPrevistaTermino);
  }

}
