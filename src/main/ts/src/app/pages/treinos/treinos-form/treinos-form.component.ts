import { Component, OnInit } from '@angular/core';
import { Treino, Pessoa, PessoaTreino, Page, TreinoExercicio } from '../../../../generated/entities';
import { FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { PessoaDialogComponent } from '../../dialogs/pessoa-dialog/pessoa-dialog.component';
import { MatDialog } from '@angular/material';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';
import { AccountService, TreinoExercicioService } from '../../../../generated/services';

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
  pessoasTreino: PessoaTreino[];
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

  loading = false;

  /**
   * 
   * @param formBuilder 
   * @param dialog 
   * @param messageService 
   */
  constructor(
    private formBuilder: FormBuilder,
    public dialog: MatDialog,
    public messageService: MensagemAlertaService,
    private pessoasService: AccountService,
    private treinoExercicioService: TreinoExercicioService
  ) { 
    this.treino = {
      treinoExercicios: [
        {
          isAtivo: true,
          exercicio: {nome: ''}
        }
      ]
    };
    this.pessoasTreino = []
    this.alunoSelecionado = {};

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

    this.formGroupStep3.controls['segunda'].setErrors({'invalido':true});

    this.formGroupStep4 = this.formBuilder.group({
      'treinoExercicios': []
    });

  }

  /* abrirDialog() {
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
  } */

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
      this.messageService.message("Selecione ao menos um dia da semana");
    }else {
      this.formGroupStep3.controls['segunda'].setErrors(null);
    }

  }

  salvar(){
    console.log('hora inicio '+ this.treino.horaPrevistaInicio);
    console.log('hora Fim '+ this.treino.horaPrevistaTermino);
  }

  listPessoasByFilters(filter: string){
    this.pessoasService.listByFilters(filter).subscribe((pessoas: Page<Pessoa>) => {
      this.pessoasList = pessoas.content;
    });
  }

  /**
   * Seleciona o aluno no auto complete
   * @param pessoa 
   */
  setAluno(pessoa: Pessoa){
    this.alunoSelecionado = pessoa;
    console.log(this.alunoSelecionado.nome);
  }

  adicionarTreinoExercicio() {
    this.treino.treinoExercicios
      .push({
        isAtivo: true,
        exercicio: {nome:'' }
      });
  }

  /**
   * Remove um treino exercicio do array, ou inativa caso já esteja salvo
   * @param idTreinoExercicio 
   */
  removerTreinoExercicio(treinoExercicioRemover: TreinoExercicio){
    const itemIndex = this.treino
      .treinoExercicios
      .findIndex((treinoExercicio) => treinoExercicio.exercicio.id == treinoExercicioRemover.exercicio.id);
      
    if(treinoExercicioRemover.id){

      this.loading = true;

      this.treinoExercicioService
      .inativarTreinoExercicio(treinoExercicioRemover.id)
        .finally( () => this.loading = false )
        .subscribe( (treinoExercicio: TreinoExercicio) => {

        this.treino.treinoExercicios[itemIndex] = treinoExercicio;

        this.messageService.message('Exercicio inativado com sucesso do treino deste aluno!<br>Este exercício não será mais realizado pelo aluno!');

      }, (error: Error) => {
        this.messageService.message(error.message);
      }) ;
        
    } else {
        this.treino.treinoExercicios.splice(itemIndex, 1);
    }

  }

}
