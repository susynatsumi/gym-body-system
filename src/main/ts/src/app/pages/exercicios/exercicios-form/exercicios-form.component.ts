import { Component, OnInit } from '@angular/core';
import { Exercicio, GrupoMuscular, Equipamento, ExercicioGrupoMuscular } from '../../../../generated/entities';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialog, MatTableDataSource } from '@angular/material';
import { EquipamentoDialogComponent } from '../../dialogs/equipamento-dialog/equipamento-dialog.component';
import { ExercicioService, ExercicioGrupoMuscularService } from '../../../../generated/services';
import { GrupoMuscularDialogComponent } from '../../dialogs/grupo-muscular-dialog/grupo-muscular-dialog.component';

import 'rxjs/add/operator/finally';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-exercicios-form',
  templateUrl: './exercicios-form.component.html',
  styleUrls: ['./exercicios-form.component.scss']
})
export class ExerciciosFormComponent implements OnInit {

  // -----------------------------------
  // -------------- ATRIBUTOS ----------
  // -----------------------------------

  // para importar imagem
  novaFoto: File;
  imageUrl =  "../../static/images/imagem-default.png";

  // para apresentação do loader
  loading = false;

  // Exercicio apresentado na tela
  exercicio: Exercicio;

  // id do exercicio passado como parametro para edicao
  parametroId: number;

  // form group do primeiro step
  formStep1Cadastro: FormGroup;
  // form group do segundo step
  formStep2Equipamentos: FormGroup;

  // form para selecionar imagem
  formImagem: FormGroup;

  //form group do terceiro e último step
  formStep3GruposMusculares: FormGroup;

  // colunas da tabela apresentada na terceira etapa do formulario
  colunas: string[] = ['nome', 'id', 'acoes'];
  // data source apresentado na table
  dadosTable: MatTableDataSource<GrupoMuscular>;

  /**
   * 
   * @param formBuilder 
   * @param dialog 
   * @param exercicioService 
   * @param mensagemService 
   */
  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    private exercicioService: ExercicioService,
    private mensagemService: MensagemAlertaService,
    private exercicioGrupoMuscularService: ExercicioGrupoMuscularService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { 

    this.exercicio = {};
    this.exercicio.exercicioGrupoMusculares = [];
    // this.gruposTable = [];
    this.dadosTable = new MatTableDataSource([]);

    this.buscaPorParametro();

  }

  // Busca de acordo com o parâmetro id passado, se não estiver null
  buscaPorParametro(){
    this.activatedRoute.params
    .subscribe(
      (paramns)=>{

        this.parametroId = paramns.id;

        if(this.parametroId != null){

          this.loading = true;

          this.exercicioService.findExercicioById(paramns.id)
            .finally(()=> this.loading = false)
            .subscribe( (exercicio: Exercicio) => {
              this.exercicio = exercicio;
              this.dadosTable = new MatTableDataSource(exercicio.exercicioGrupoMusculares);
            }, (error: Error) =>{
              this.mensagemService.errorFind(error.message);
            }
          );

        }

      }

    );

  }

  ngOnInit() {

    this.formStep1Cadastro = this.formBuilder.group({
      'nome': [this.exercicio.nome, Validators.compose([Validators.required, Validators.maxLength(60)])],
      'descricao': [this.exercicio.descricao, Validators.compose([Validators.required, Validators.maxLength(500)])],
      // 'linkVideo' : [this.exercicio.linkVideo],
      'isAtivo': [this.exercicio.isAtivo, Validators.required]
    });

    this.formImagem = this.formBuilder.group({
      imagemFileTransfer: [this.exercicio.imagemFileTransfer]
    });

    if(this.parametroId == null) {
      this.formStep1Cadastro.controls['isAtivo'].disable();
      this.exercicio.isAtivo = true;
    } else {
      this.formStep1Cadastro.controls['isAtivo'].enable()
    }

    this.formStep2Equipamentos = this.formBuilder.group({
      'equipamento': [this.exercicio.equipamento, Validators.required]
    });

    this.formStep2Equipamentos.controls['equipamento'].disable();

    this.formStep3GruposMusculares = this.formBuilder.group({
      'gruposMusculares': []
    });

    // this.gruposMusculares
    //   .valueChanges
    //   .pipe(
    //     mapTo(this.gruposMusculares.getRawValue())
    //   )
    //   .subscribe((dados)=>{
    //     this.dadosTable = new MatTableDataSource(dados);
    //     console.log('\n\n\n\n\n dados da minha table');
    //     console.log(dados);
    //   });

    // console.log(this.formStep3GruposMusculares);

  }

  // ---------------------------------------------------
  // ----------------- metodos para manipular imagem -------------
  // ---------------------------------------------------

  /**
   * Remove o equipamento da tela
   */
  removerImagem(){
    this.exercicio.imagem = null;
    this.exercicio.imagemFileTransfer = null;
    this.novaFoto = null;
    this.imageUrl =  "../../static/images/imagem-default.png";
  }

  removerImagemInServer(){

    if(this.exercicio == null || this.exercicio.id == null){
      return;
    }

    if(!confirm('Deseja remover esta imagem?')) {
      return;
    }

    this.exercicioService.removerImagem(this.exercicio.id).subscribe(
      ()=>{
        this.removerImagem();
      }
    );
  }

  /**
   * Seleciona a imagem e altera a apresentada no compenent img
   * @param event 
   */
  setArquivo(event) {
    
    this.removerImagem();

    if (event.target.files[0]){
      this.exercicio.imagemFileTransfer = event.target;

      this.novaFoto = event.target.files.item(0);
  
      //Show image preview
      var reader = new FileReader();
      reader.onload = (event:any) => {
        this.imageUrl = event.target.result;
      }
  
      reader.readAsDataURL(this.novaFoto);

    }
   
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
      equipamento.imagem = null;
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

  /**
   * Retorna uma lista de ids de grupos musculares já selecionados
   */
  get notInIds(){
    let ids: number[] = [];

    this.exercicio.exercicioGrupoMusculares
      .forEach((exercicioGrupoMuscular: ExercicioGrupoMuscular) =>{
        ids.push(exercicioGrupoMuscular.grupoMuscular.id);
      });

    return ids;
  }

  /**
   * Abre dialog para selecionar um grupo muscular
   */
  abrirDialogGrupo() {

    const dialogRef = this.dialog.open(GrupoMuscularDialogComponent, {
      width: '80%',
      height: '90%',
      data: this.notInIds
    });

    dialogRef.afterClosed().subscribe((grupoMuscular:GrupoMuscular) => {
      if(grupoMuscular != null && grupoMuscular.id != null){
        this.adicionarTableRow(grupoMuscular);
      }
    });

  }

  /**
   * Adiciona grupo muscular na lista e na table
   * @param grupoMuscular 
   */
  adicionarTableRow(grupoMuscular){

    const existente = this.exercicio
      .exercicioGrupoMusculares.filter(
        (grupo: GrupoMuscular) => 
          grupo.id == grupoMuscular.id
      );

    if(existente != null && existente.length != 0){
      return;
    }

    let novoExercicioGrupoMuscular: ExercicioGrupoMuscular = {
      grupoMuscular: grupoMuscular
    } 

    this.exercicio
      .exercicioGrupoMusculares.push(novoExercicioGrupoMuscular);

    this.atualizarTable();

  }

  // referencia https://github.com/angular/material2/issues/9117
  /**
   * Atualiza os dados da table
   */
  atualizarTable(){

    this.dadosTable = new MatTableDataSource(
      this.exercicio.exercicioGrupoMusculares
    );

  } 

  /**
   * valida se existe algum grupo muscular selecionado
   */
  validaGruposSelecionadosValidos(){
    return this.exercicio.exercicioGrupoMusculares
      && this.exercicio.exercicioGrupoMusculares.length != 0;
  }

  /**
   * Salva um exercicio no servidor
   */
  salvar(){

    if(!this.validaGruposSelecionadosValidos()){
      this.mensagemService.message('Selecione ao menos um grupo muscular');
      return;
    }

    this.loading = true;
    // unica forma que encontrei para não dar erro
    this.exercicio.equipamento.imagem = null;
    this.exercicio.imagem = null;

    this.enviar()
      .finally(() => this.loading = false)
      .subscribe(
        ()=> {
         this.router. navigate(["exercicios"]);
        }, (error: Error)=>{
          this.mensagemService.errorSave(error.message)
        });

  }

  enviar(){
    if( this.parametroId == null ){
      return this.exercicioService.insertExercicio(this.exercicio);
    } else {
      return this.exercicioService.updateExercicio(this.exercicio);
    }

  }

  /**
   * Remove grupo muscular do exercicio de acordo com o id passado
   * @param idGrupoMuscular 
   */
  removerItem(idGrupoMuscular){
      const itemIndex = this.exercicio.exercicioGrupoMusculares.findIndex(exercicioGrupo => 
        exercicioGrupo.grupoMuscular.id === idGrupoMuscular
      );

      let exercicioGrupo = this.exercicio
        .exercicioGrupoMusculares[itemIndex];

      this.exercicio.exercicioGrupoMusculares.splice(itemIndex, 1);
      this.dadosTable = new MatTableDataSource(this.exercicio.exercicioGrupoMusculares);

      this.removerExercicioGrupoMuscular(exercicioGrupo);

  }

  /**
   * Remove exercicio grupo muscular da base
   * @param exercicioGrupoMuscular 
   */
  removerExercicioGrupoMuscular(exercicioGrupoMuscular: ExercicioGrupoMuscular){
    if(exercicioGrupoMuscular != null && exercicioGrupoMuscular.id != null){
      this.loading = true;
      this.exercicioGrupoMuscularService.deleteById(exercicioGrupoMuscular.id)
      .finally( () => this.loading = false )
      .subscribe(() => {
        // remover com sucesso
      }, (error: Error) =>{
        this.mensagemService.errorRemove(error.message);
      });
    }
  }

  // add(){
  //   this.gruposMuscularesTable.content.push({nome:"sadfsadfasafsd", id: 156});
  //   this.dadosTable = new MatTableDataSource(this.gruposMuscularesTable.content);
  // }

  /**
   * Cria um item do form array de grupos musculares
   */
  // criaItemFormGrupoMuscular(grupoMuscular): FormGroup{
  //   return this.formBuilder.group({
  //     'grupoMuscular': [grupoMuscular, Validators.required],
  //   });
  // }

  /**
   * Getter para o array do formulário
   */
  // get gruposMusculares(): FormArray {
  //   return this.formStep3GruposMusculares
  //     .get('gruposMusculares') as FormArray;
  // }

  /**
   * Abre dialog para pesquisar um grupo muscular e adiciona
   * item selecionado ao form Array
   */
  // adicionarGrupoMuscular(){

  //   const exercicio = this.criaItemFormGrupoMuscular();
  //   this.gruposMusculares.push(exercicio);

  // }

}
