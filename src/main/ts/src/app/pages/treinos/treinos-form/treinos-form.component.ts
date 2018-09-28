import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Treino, Pessoa, Page, TreinoExercicio, PageRequest, Exercicio, TipoTreinoExercicioValues, TipoTreinoExercicio } from '../../../../generated/entities';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { MatStepper } from '@angular/material';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';
import { AccountService, TreinoService, ExercicioService } from '../../../../generated/services';
import { Router, ActivatedRoute } from '../../../../../node_modules/@angular/router';

import 'rxjs/operator/finally';

@Component({
  selector: 'app-treinos-form',
  templateUrl: './treinos-form.component.html',
  styleUrls: ['./treinos-form.component.scss']
})
export class TreinosFormComponent implements OnInit {

  // ---------------------------------------------------
  // -------------------- ATRIBUTOS --------------------
  // ---------------------------------------------------

  // data atual para data minima para selecionar
  public minDate = new Date();

  // treino populado pela tela
  treino: Treino;
  alunoSelecionado: Pessoa;

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

  // pessoas para preencher o auto complete
  public pessoasList: Pessoa[];

  // para apresentar ou não o loader
  loading = false;

  // item atual do expansion panel
  itemAtual = -1;

  // parametro que define se está em edicao ou nao
  parametroId: number = null;

  pessoaLogada: Pessoa;

  pageRequest: PageRequest;

  // exercicios para popular o auto complete
  exerciciosAutoComplete: Treino[];

  // tipos de treino exercicio para o select
  tiposTreinoExercicio: string[];

  // ----------------------------------------------
  // --------------------- CONSTRUCTOR ------------
  // ----------------------------------------------

  /**
   * 
   * @param formBuilder 
   * @param messageService 
   * @param pessoasService 
   * @param treinoService 
   * @param router 
   * @param activedRoute 
   * @param pessoaLogadaSession 
   * @param exercicioService 
   */
  constructor(
    private formBuilder: FormBuilder,
    private messageService: MensagemAlertaService,
    private pessoasService: AccountService,
    private treinoService: TreinoService,
    private router: Router,
    private activedRoute: ActivatedRoute,
    private pessoaLogadaSession: AccountService,
    private exercicioService: ExercicioService,
  ) {

    this.parametroId = null;

    this.treino = {
      treinoExercicios: [],
      dataFim: null,
      dataInicio: null,
      horaPrevistaInicio: null,
      horaPrevistaTermino: null,
    };

    this.tiposTreinoExercicio = TipoTreinoExercicioValues;

    this.pessoaLogadaSession.getPessoaLogada()
    .subscribe((pessoa: Pessoa) => {
      this.pessoaLogada = pessoa;
    });

    this.alunoSelecionado = {};

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

  // -----------------------------------------
  // ----------------- MÉTODOS ---------------
  // -----------------------------------------

  /**
   * Inicializa os itens do form
   */
  ngOnInit() {

    this.formGrupoStep1 = this.formBuilder.group({
      'alunoSelecionado': [this.alunoSelecionado, Validators.required]
    });

    this.formGrupoStep2 = this.formBuilder.group({
      'nome': [this.treino.nome, Validators.compose([Validators.required, Validators.minLength(1)])],
      'dataInicio': [this.treino.dataInicio, Validators.required],
      'dataFim': [this.treino.dataFim, Validators.required],
      'horaPrevistaInicio': [this.treino.horaPrevistaInicio],
      'horaPrevistaTermino': [this.treino.horaPrevistaTermino],
    });

    this.formGrupoStep2.controls['dataInicio'].disable();
    this.formGrupoStep2.controls['dataFim'].disable();

    this.formGroupStep3 = this.formBuilder.group({
      'segunda': [],
      'terca': [],
      'quarta': [],
      'quinta': [],
      'sexta': [],
      'sabado': [],
      'domingo': [],
    });

    // inicio um form array vazio, para ou popular no modo edição
    // ou para criar um item posteriormente caso esteja no modo de criação
    this.formGroupStep4 = this.formBuilder.group({
      'treinoExercicios': this.formBuilder.array([])
    });

    this.inicializaTreino();

  }

  /**
   * carrega um treino para o form caso esteja no modo de edição
   * caso contrário cria um novo form grup e adiciona ao form array, para cadastro
   */
  inicializaTreino() {
    
    this.activedRoute.params
      .subscribe(
        (paramns) => {

          this.parametroId = paramns.id;

          if (this.parametroId != null) {

            this.loading = true;
            this.itemAtual = 0;

            this.treinoService.findTreinoById(this.parametroId)
              .finally(() => this.loading = false)
              .subscribe((treino: Treino) => {
                this.carregaTreinoExercicioFormGroup(treino);
                this.treino = treino;
                this.alunoSelecionado = treino.aluno;
              });

          } else {
            this.addNewTreinoExercicioFormArray();
            console.log(this.formGroupStep4);
          }

        }

      );

  }

  // ------------------------------------------------------------------
  // -------------------------- MÉTODOS STEP 2 ------------------------
  // ------------------------------------------------------------------

  /**
   * Faz a listagem de pessoa de acordo com o texto digitado no autocomplete
   * @param filter 
   */
  listPessoasByFilters(filter: string) {
    this.pessoasService.listByFilters(filter, true, true, this.pageRequest)
      .subscribe((pessoas: Page<Pessoa>) => {
        this.pessoasList = pessoas.content;
      });
  }

  /**
   * Seleciona o aluno no auto complete
   * @param pessoa 
   */
  setAluno(pessoa: Pessoa) {
    this.alunoSelecionado = pessoa;
  }

  // -------------------------------------------------------------------
  // -------------------------- MÉTODOS STEP 3 -------------------------
  // -------------------------------------------------------------------

  /**
   * Percorre a lista de check box e adiciona os selecionados na lista
   */
  selecionarDiasSemana(stepper: MatStepper) {

    if (this.parametroId === null) {
      return;
    }

    this.formGroupStep3.controls['segunda'].setErrors({ 'invalido': true });
    this.treino.diasSemanaSelecionados = [];

    if (this.segunda) {
      this.treino.diasSemanaSelecionados.push('SEGUNDA');
    }
    if (this.terca) {
      this.treino.diasSemanaSelecionados.push('TERCA');
    }
    if (this.quarta) {
      this.treino.diasSemanaSelecionados.push('QUARTA');
    }
    if (this.quinta) {
      this.treino.diasSemanaSelecionados.push('QUINTA');
    }
    if (this.sexta) {
      this.treino.diasSemanaSelecionados.push('SEXTA');
    }
    if (this.sabado) {
      this.treino.diasSemanaSelecionados.push('SABADO');
    }
    if (this.domingo) {
      this.treino.diasSemanaSelecionados.push('DOMINGO');
    }

    if (this.treino.diasSemanaSelecionados.length == 0) {
      this.messageService.message("Selecione ao menos um dia da semana");
    } else {
      this.formGroupStep3.controls['segunda'].setErrors(null);
      stepper.next();
    }

  }


  // // ------------------------------------------------------
  // // ---------- MÉTODOS STEP 4, FORM TREINO EXERCICIO -----
  // // ------------------------------------------------------


 /**
   * Busca os exercicios para o autocomplete
   * @param filtro 
   */
  listExerciciosByFilters( filtro: string ){

    this.exercicioService.listExerciciosByFilters(filtro)
      .subscribe((exercicios: Page<Exercicio>) =>{
        this.exerciciosAutoComplete = exercicios.content;
      }, (error: Error)=>{
        this.messageService.errorFind(error.message);
      });
  }

 /**
   * Seta o exercicio do autocomplete
   * @param treinoExercicio 
   * @param exercicio 
   */
  setExercicio(indice, exercicio: Exercicio){

    console.log(indice);

    if(indice != null){

      this.treino.treinoExercicios[indice].exercicio = exercicio;
      console.log(this.treino.treinoExercicios[indice].exercicio);
    }

  }

  /**
   * Altera os campos obrigatórios de acordo com o tipo treino exercicio selecionado
   * 
   * @param formGroup 
   * @param indice 
   * @param tipoSelecionado 
   */
  changeTipoTreinoExercicio(formGroup: FormGroup,  indice, tipoSelecionado: TipoTreinoExercicio) {

    formGroup.get('carga').clearValidators();
    formGroup.get('carga').updateValueAndValidity({emitEvent:false, onlySelf:true});

    formGroup.get('repeticoes').clearValidators();
    formGroup.get('repeticoes').updateValueAndValidity({emitEvent:false, onlySelf:true});

    formGroup.get('tempoMin').clearValidators();
    formGroup.get('tempoMin').updateValueAndValidity({emitEvent:false, onlySelf:true});

    if (tipoSelecionado === 'CARGA_REPETICOES') {
      formGroup.get('carga').setValidators([Validators.required]);
      formGroup.get('repeticoes').setValidators([Validators.required]);
    } else if (tipoSelecionado === 'REPETICOES') {
      formGroup.get('repeticoes').setValidators([Validators.required]);
    } else {
      formGroup.get('tempoMin').setValidators([Validators.required]);
    }

    this.treino.treinoExercicios[indice].tipoTreinoExercicio = tipoSelecionado;

  }

  /**
   * Remove um treino exercicio do array, ou inativa caso já esteja salvo
   * @param idTreinoExercicio 
   */
  removerTreinoExercicio(treinoExercicioRemover: TreinoExercicio) {
    const itemIndex = this.treino
      .treinoExercicios
      .findIndex((treinoExercicio) => treinoExercicio.exercicio.id == treinoExercicioRemover.exercicio.id);

    if (treinoExercicioRemover.id) {
      this.messageService.message("Não é possível remover um exercício de um treino");
    } else {
      this.treino.treinoExercicios.splice(itemIndex, 1);
      this.itemAtual = this.itemAtual - 1;
    }

  }

  /**
   * Adiciona novo item no form array
   */
  addNewTreinoExercicioFormArray() {

    if (this.formArrayTreinoExercicio.invalid) {
      this.messageService.message('Preencha os campos obrigatórios!');
      return;
    }

    this.formArrayTreinoExercicio.push(this.newTreinoExercicioFormGroup());

    this.itemAtual = this.itemAtual + 1;

  }

  /**
   * Para atualizar o item atual do expansion panel, expandindo somente ele
   * @param itemAtual 
   */
  setItemAtual(itemAtual: number) {

    if (itemAtual) {
      this.itemAtual = itemAtual;
    }
    
  }

  get formArrayTreinoExercicio() : FormArray {
    return this.formGroupStep4.get('treinoExercicios') as FormArray;
  }

  /**
   * Cria novo form group para treino exericicio
   */
  newTreinoExercicioFormGroup() {

    console.log('Novo treino exercicio');

    let treinoExercicio: TreinoExercicio = {
      exercicio: {
        nome: ''
      },
      tipoTreinoExercicio: 'CARGA_REPETICOES'
    };

    this.treino.treinoExercicios.push(treinoExercicio);

    return this.formBuilder.group({
      'exercicio': [
        {},
        Validators.required
      ],
      'tipoTreinoExercicio': [
        treinoExercicio.tipoTreinoExercicio,
        Validators.required
      ],
      'series': [
        treinoExercicio.series,
        Validators.compose([
          Validators.required,
          Validators.min(1)
        ])
      ],
      'carga': [treinoExercicio.carga, Validators.compose([Validators.required, Validators.min(1)])],
      'repeticoes': [treinoExercicio.repeticoes, Validators.compose([Validators.required, Validators.min(1)])],
      'tempoMin': [treinoExercicio.tempoMin],
      'observacoes': [treinoExercicio.observacoes]
    });

  }

  /**
   * Insere treino exercicios em um form array
   */
  carregaTreinoExercicioFormGroup(treino: Treino) {

    let controles = <FormArray> this.formGroupStep4.controls.treinoExercicios;
  
    treino.treinoExercicios.forEach((treinoExercicio: TreinoExercicio)=>{
      
      console.log(treinoExercicio);

      let form: FormGroup = this.formBuilder.group({
        exercicio: [
          treinoExercicio.exercicio,
          Validators.required
        ],
        tipoTreinoExercicio: [
          treinoExercicio.tipoTreinoExercicio,
          Validators.required
        ],
        series: [
          treinoExercicio.series,
          Validators.compose([
            Validators.required,
            Validators.min(1)
          ])
        ],
        carga: [
          treinoExercicio.carga, 
          ( 
            treinoExercicio.tipoTreinoExercicio === 'CARGA_REPETICOES'
              ? 
                Validators.compose([Validators.required, Validators.min(1)]) 
              : 
                null  
          ) 
        ],
        repeticoes : [treinoExercicio.repeticoes,
          ( 
            treinoExercicio.tipoTreinoExercicio === 'CARGA_REPETICOES' || treinoExercicio.tipoTreinoExercicio === 'REPETICOES'
              ? 
                Validators.compose([Validators.required, Validators.min(1)]) 
              : 
                null  
          ) 
        ],
        tempoMin: [treinoExercicio.tempoMin, 
            ( 
              treinoExercicio.tipoTreinoExercicio === 'TEMPO'
                ? 
                  Validators.compose([Validators.required, Validators.min(1)]) 
                : 
                  null  
            ) 
        ],
        observacoes: [treinoExercicio.observacoes]
      });
    
      console.log(form);

      controles.push(form);

    });
    
    console.log(this.formGroupStep4.controls.treinoExercicios);

  }

  // -----------------------------------------------------------
  // ------------------- SALVAR --------------------------------
  // -----------------------------------------------------------

   /**
   * Salva os dados no servidor
   */
  salvar() {

    if (this.formGroupStep4.invalid) {
      this.messageService.message('Preencha todos os campos obrigatórios');
      return;
    }

    this.loading = true;

    console.log(this.treino);

    // this.treino.treinoExercicios = this.formArrayTreinoExercicio.value;

    // console.log(this.formArrayTreinoExercicio.value);
    // console.log(this.treino.treinoExercicios);

    this.enviar()
      .finally(() => this.loading = false)
      .subscribe(() => {
        this.messageService.messageBottom('Treino salvo com sucesso!');
        this.router.navigate(['treinos']);
      }, (erro: Error) => {
        this.messageService.errorSave(erro.message);
      });
  }

  /**
   * Envia os dados para o servidor
   */
  private enviar() {

    if (this.parametroId == null) {

      this.treino.personal = this.pessoaLogada;
      this.treino.aluno = this.alunoSelecionado;

      return this.treinoService.insertTreino(this.treino);

    }

    return this.treinoService.updateTreino(this.treino);

  }

}
