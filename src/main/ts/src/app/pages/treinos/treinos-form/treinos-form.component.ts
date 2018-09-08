import { Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Treino, Pessoa, Page, TreinoExercicio, PageRequest } from '../../../../generated/entities';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { MatStepper } from '@angular/material';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';
import { AccountService, TreinoExercicioService, TreinoService } from '../../../../generated/services';
import { Router, ActivatedRoute } from '../../../../../node_modules/@angular/router';

@Component({
  changeDetection: ChangeDetectionStrategy.Default,
  selector: 'app-treinos-form',
  templateUrl: './treinos-form.component.html',
  styleUrls: ['./treinos-form.component.scss']
})
export class TreinosFormComponent implements OnInit {

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
  itemAtual = 0;

  // parametro que define se está em edicao ou nao
  parametroId: number = null;

  pessoaLogada: Pessoa;

  pageRequest: PageRequest;

  /**
   * 
   * @param formBuilder 
   * @param dialog 
   * @param messageService 
   * @param pessoasService 
   * @param treinoExercicioService 
   */
  constructor(
    private formBuilder: FormBuilder,
    private messageService: MensagemAlertaService,
    private pessoasService: AccountService,
    private treinoExercicioService: TreinoExercicioService,
    private treinoService: TreinoService,
    private router: Router,
    private activedRoute: ActivatedRoute,
    private pessoaLogadaSession: AccountService
  ) { 

    this.pessoaLogadaSession.getPessoaLogada()
      .subscribe((pessoa: Pessoa)=>{
        this.pessoaLogada = pessoa;
    });

    this.treino = {
      treinoExercicios: [
        {
          tipoTreinoExercicio: 'CARGA_REPETICOES',
          exercicio: {nome: ''}
        }
      ],
      dataFim: null, 
      dataInicio: null,
      horaPrevistaInicio: null, 
      horaPrevistaTermino: null,
    };
    this.alunoSelecionado = {};

    this.inicializaTreino();

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

  inicializaTreino(){

    this.activedRoute.params
    .subscribe(
      (paramns)=>{

        this.parametroId = paramns.id;

        if(this.parametroId != null){

          this.loading = true;

          this.treinoService.findTreinoById(this.parametroId)
            .finally(()=> this.loading = false)
            .subscribe((treino: Treino)=>{
              this.treino = treino;
              this.alunoSelecionado = treino.aluno;
            });

        }

      }

    );

  }

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

    this.formGroupStep4 = this.formBuilder.group({
      'treinoExercicios': this.formBuilder.array([
        this.newTreinoExercicioFormGroup()
      ])
    });

  }

  /**
   * Percorre a lista de check box e adiciona os selecionados na lista
   */
  selecionarDiasSemana(stepper: MatStepper){

    if(this.parametroId == null){
      return;
    }

    this.formGroupStep3.controls['segunda'].setErrors({'invalido':true});
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
      this.messageService.message("Selecione ao menos um dia da semana");
    }else {
      this.formGroupStep3.controls['segunda'].setErrors(null);
      stepper.next();
    }

  }

  /**
   * Faz a listagem de pessoa de acordo com o texto digitado no autocomplete
   * @param filter 
   */
  listPessoasByFilters(filter: string){
    this.pessoasService.listByFilters(filter, true, true, this.pageRequest)
    .subscribe((pessoas: Page<Pessoa>) => {
      this.pessoasList = pessoas.content;
    });
  }

  /**
   * Seleciona o aluno no auto complete
   * @param pessoa 
   */
  setAluno(pessoa: Pessoa){
    this.alunoSelecionado = pessoa;
  }

  // adicionarTreinoExercicio() {
  //   this.treino.treinoExercicios
  //     .push({
  //       isAtivo: true,
  //       exercicio: {nome:'' }
  //     });
  // }

  /**
   * Remove um treino exercicio do array, ou inativa caso já esteja salvo
   * @param idTreinoExercicio 
   */
  removerTreinoExercicio(treinoExercicioRemover: TreinoExercicio){
    const itemIndex = this.treino
      .treinoExercicios
      .findIndex((treinoExercicio) => treinoExercicio.exercicio.id == treinoExercicioRemover.exercicio.id);
      
    if(treinoExercicioRemover.id){
      this.messageService.message("Não é possível remover um exercício de um treino");
    } else {
        this.treino.treinoExercicios.splice(itemIndex, 1);
        this.itemAtual = this.itemAtual - 1;
    }

  }

  /**
   * Adiciona novo item no form array
   */
  addNewTreinoExercicioFormArray(){

    // this.formArrayTreinoExercicio.controls.forEach( (group: FormGroup)=>{
      // group.updateValueAndValidity();
    // });

    if(this.formArrayTreinoExercicio.invalid ){
      this.messageService.message('Preencha os campos obrigatórios!');
      return;
    }

    this.itemAtual = this.itemAtual + 1;
    this.formArrayTreinoExercicio.push(this.newTreinoExercicioFormGroup());

  }

  /**
   * Para atualizar o item atual do expansion panel, expandindo somente ele
   * @param itemAtual 
   */
  setItemAtual(itemAtual:number){
    if(itemAtual != null){
      this.itemAtual = itemAtual;
    }
  }

  /**
   * Get form Array
   */
  get formArrayTreinoExercicio(){
    return this.formGroupStep4.get('treinoExercicios') as FormArray;
  }

  /**
   * Cria novo form group para treino exericicio
   */
  newTreinoExercicioFormGroup(){

    let treinoExercicio: TreinoExercicio = {
      exercicio: {
        nome: ''
      },
      tipoTreinoExercicio: 'CARGA_REPETICOES',
      treino: this.treino
    };
    
    this.treino.treinoExercicios.push(treinoExercicio);

    return this.formBuilder.group({
      'exercicio': [
        treinoExercicio.exercicio, 
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
   * Salva os dados no servidor
   */
  salvar(){

    if(this.formGroupStep4.invalid){
      this.messageService.message('Preencha todos os campos obrigatórios');
      return;
    }

    this.loading = true;

    this.enviar()
      .finally( ()=> this.loading = false )
      .subscribe(()=>{
        this.messageService.messageBottom('Treino salvo com sucesso!');
        this.router.navigate(['treinos']);
      }, (erro: Error)=>{
        this.messageService.errorSave(erro.message);
      });
  }

  /**
   * Envia os dados para o servidor
   */
  private enviar(){

    if(this.parametroId == null){

      this.treino.personal = this.pessoaLogada;
      this.treino.aluno = this.alunoSelecionado;

      return this.treinoService.insertTreino(this.treino);

    }

    return this.treinoService.updateTreino(this.treino);

  }

}
