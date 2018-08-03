import { Component, OnInit } from '@angular/core';
import { Exercicio, GrupoMuscular, Equipamento, Page } from '../../../../generated/entities';
import { FormGroup, FormBuilder, Validators, FormControl, FormArray } from '../../../../../node_modules/@angular/forms';
import { MatDialog, MatTableDataSource } from '../../../../../node_modules/@angular/material';
import { EquipamentoDialogComponent } from '../../dialogs/equipamento-dialog/equipamento-dialog.component';
import { GrupoMuscularService } from '../../../../generated/services';
import { GrupoMuscularDialogComponent } from '../../dialogs/grupo-muscular-dialog/grupo-muscular-dialog.component';

import { mapTo } from 'rxjs/operators';

@Component({
  selector: 'app-exercicios-form',
  templateUrl: './exercicios-form.component.html',
  styleUrls: ['./exercicios-form.component.scss']
})
export class ExerciciosFormComponent implements OnInit {

  // -----------------------------------
  // -------------- ATRIBUTOS ----------
  // -----------------------------------

  exercicio: Exercicio;

  parametroId: number;

  gruposTable: GrupoMuscular[];

  formStep1Cadastro: FormGroup;
  formStep2Equipamentos: FormGroup;
  formStep3GruposMusculares: FormGroup;

  tiles = [
    {text: 'One', cols: 3, rows: 1, color: 'lightblue'},
    {text: 'Two', cols: 1, rows: 2, color: 'lightgreen'},
    {text: 'Three', cols: 1, rows: 1, color: 'lightpink'},
  ];

  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private grupoMuscularService: GrupoMuscularService
  ) { 

    this.exercicio = {};
    this.gruposTable = [];

  }

  ngOnInit() {

    this.formStep1Cadastro = this.formBuilder.group({
      'nome': [this.exercicio.nome, Validators.compose([Validators.required, Validators.maxLength(60)])],
      'descricao': [this.exercicio.descricao, Validators.compose([Validators.required, Validators.maxLength(500)])],
      'linkVideo' : [this.exercicio.linkVideo],
      'isAtivo': [this.exercicio.isAtivo, Validators.required]
    });

    (
      this.parametroId == null?
        this.formStep1Cadastro.controls['isAtivo'].disable()
      :
        this.formStep1Cadastro.controls['isAtivo'].enable()
    )

    this.formStep2Equipamentos = this.formBuilder.group({
      'equipamento': [this.exercicio.equipamento, Validators.required]
    });

    this.formStep2Equipamentos.controls['equipamento'].disable();

    this.formStep3GruposMusculares = this.formBuilder.group({
      'gruposMusculares': this.formBuilder.array([
        this.criaItemFormGrupoMuscular(null)
      ])
    });

    this.gruposMusculares
      .valueChanges
      .pipe(
        mapTo(this.gruposMusculares.getRawValue())
      )
      .subscribe((dados)=>{
        this.dadosTable = new MatTableDataSource(dados);
        console.log('\n\n\n\n\n dados da minha table');
        console.log(dados);
      });

    

    console.log(this.formStep3GruposMusculares);

  }

  // ---------------------------------------------------
  // ----------------- metodos equipamento -------------
  // ---------------------------------------------------

  /**
   * Abre dialog para pesquisar um equipamento 
   * e seta no exercicio o equipamento selecionado
   */
  abrirDialog() {
    const dialogRef = this.dialog.open(EquipamentoDialogComponent, {
      width: '80%',
      height: '90%',
      data: this.exercicio.equipamento
    });

    dialogRef.afterClosed().subscribe((equipamento:Equipamento) => {

      this.formStep2Equipamentos.setValue({
        'equipamento': equipamento,
      },
      {
        emitEvent: true, 
        onlySelf: false
      });

    });

  }

  // --------------------------------------------- -----
  // ----------------- métodos grupos musculares -------
  // ----------------------------------------------------

  colunas: string[] = ['nome', 'id'];
  dadosTable = new MatTableDataSource;
  gruposMuscularesTable:  Page<GrupoMuscular>;

  abrirDialogGrupo() {
    const dialogRef = this.dialog.open(GrupoMuscularDialogComponent, {
      width: '80%',
      height: '90%',
    });

    dialogRef.afterClosed().subscribe((grupoMuscular:GrupoMuscular) => {
      this.adicionarTableRow(grupoMuscular);
    });

  }

  adicionarTableRow(grupoMuscular){

    const existente = this.gruposTable.filter((grupo: GrupoMuscular)=> grupo.id == grupoMuscular.id);

    if(existente != null && existente.length != 0){
      return;
    }

    let novo: FormGroup = this.criaItemFormGrupoMuscular(grupoMuscular);
    
    this.gruposMusculares.push(novo);

    this.gruposTable.push(grupoMuscular);

    console.log(this.gruposTable);

    this.atualizarTable();
  }

  atualizarTable(){
    console.log('Atualizando table');

    // referencia https://github.com/angular/material2/issues/9117

    this.printar();

    this.dadosTable = new MatTableDataSource(this.gruposTable);

  } 

  add(){
    console.log('adadafda');
    this.gruposMuscularesTable.content.push({nome:"sadfsadfasafsd", id: 156});
    this.dadosTable = new MatTableDataSource(this.gruposMuscularesTable.content);
  }

  /**
   * Cria um item do form array de grupos musculares
   */
  criaItemFormGrupoMuscular(grupoMuscular): FormGroup{
    return this.formBuilder.group({
      'grupoMuscular': [grupoMuscular, Validators.required],
    });
  }

  /**
   * Getter para o array do formulário
   */
  get gruposMusculares(): FormArray {
    return this.formStep3GruposMusculares
      .get('gruposMusculares') as FormArray;
  }

  /**
   * Abre dialog para pesquisar um grupo muscular e adiciona
   * item selecionado ao form Array
   */
  // adicionarGrupoMuscular(){

  //   const exercicio = this.criaItemFormGrupoMuscular();
  //   this.gruposMusculares.push(exercicio);

  // }

 

  printar(){
    console.log(this.formStep3GruposMusculares);
  }

}
