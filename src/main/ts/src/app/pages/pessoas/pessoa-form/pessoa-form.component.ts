import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Pessoa, GeneroValues, PapelValues, Papel } from '../../../../generated/entities';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';
import { AccountService } from '../../../../generated/services';

import 'rxjs/add/operator/finally';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';
import { UsuarioSessaoService } from '../../../services/usuario-sessao-service';

@Component({
  selector: 'pessoa-form',
  templateUrl: './pessoa-form.component.html',
  styleUrls: ['./pessoa-form.component.scss'],
})
export class PessoaFormComponent implements OnInit {

  // ---------------------------------------------
  // ---------------- ATRIBUTOS ------------------
  // ---------------------------------------------

  // form groups
  formStep1DadosPessoas: FormGroup;
  formStep2DadosAcesso: FormGroup;
  formStep3Permissoes: FormGroup;
  formStep4Objetivo: FormGroup;

  // objeto do form
  pessoa: Pessoa;

  // generos para o selecet
  generos: string[];

  // papeis carregados no select
  papeis: string[];

  // parametro id passado por parametro na url
  parametroId: number;

  // usuário logado no sistema
  pessoaLogada: Pessoa;

  // para apresentar ou não um loader na tela
  public loading = false;

  public maxDate = new Date();

  // ---------------------------------------------
  // ---------------- CONSTRUCTOR ----------------
  // ---------------------------------------------

  constructor(
    private formBuilder: FormBuilder,
    private pessoaService: AccountService,
    private route: ActivatedRoute,
    private router: Router,
    private mensagemAlerta: MensagemAlertaService,
    private usuarioSessaoService: UsuarioSessaoService
  ) {

    this.pessoaLogada = this.usuarioSessaoService.usuarioLogado;
    this.pessoa = {};

    this.route.params.subscribe(
      paramns => {
        this.parametroId = paramns.id

        if(this.parametroId != null){

          this.loading = true;

          this.pessoaService.findPessoaById(this.parametroId)
          .finally( () => this.loading = false )
          .subscribe(
            (pessoa: Pessoa) =>{
              this.pessoa = pessoa
            }, (error: Error) =>{
              this.mensagemAlerta.errorFind(error.message);
              this.router.navigate(['pessoas']);
            }
          );
        }else {
          this.pessoa.isAtivo = true;
        }
      });

  }

  // ---------------------------------------------
  // ---------------- MÉTODOS   ------------------
  // ---------------------------------------------

  /**
   * Inicialização dos form groups
   */
  ngOnInit() {
    
    this.formStep1DadosPessoas = this.formBuilder.group({
      'nome': [this.pessoa.nome, Validators.compose([Validators.required, Validators.minLength(4)])],
      'genero': [this.pessoa.genero, Validators.required],
      'dataNascimento': [this.pessoa.dataNascimento, Validators.required],
    });

    this.formStep1DadosPessoas.controls['dataNascimento'].disable();

    this.formStep2DadosAcesso = this.formBuilder.group({
      'email': [this.pessoa.email, Validators.compose([Validators.required, Validators.minLength(4)])],
      'login': [this.pessoa.login],
      // 'senha': [this.pessoa.senha],
    });

    this.formStep3Permissoes = this.formBuilder.group({
      'papeis': [this.pessoa.papeis, Validators.required],
      'isAtivo': this.pessoa.isAtivo
    });

    if(!this.parametroId){
      this.formStep3Permissoes.controls['isAtivo'].disable();
    }

    this.formStep4Objetivo = this.formBuilder.group({
      'objetivo': [this.pessoa.objetivo],
    });

    this.generos = GeneroValues;
    this.papeis = PapelValues;

    if( !this.usuarioSessaoService.isAdministrador ){
      this.papeis = this.papeis.filter(papel => papel.toString() !== 'ADMINISTRATOR');
    }

  }

  /**
   * Requisita ao servidor para salvar os dados do form
   */
  salvar(){

    console.log(this.pessoa.dataNascimento);
    
    this.loading = true;

    if(this.parametroId == null){
      this.pessoaService.insertPessoa(this.pessoa)
      .finally( ()=> this.loading = false )
      .subscribe((pessoa: Pessoa) => {
        this.mensagemAlerta.message('Dados salvos com sucesso!');
      },(error: Error)=>{
        this.mensagemAlerta.message(error.message);
        //error
      },() =>{
        this.router.navigate(['/pessoas']);
      }
    );

    } else {

      this.pessoaService.updatePessoa(this.pessoa)
      .finally( ()=> this.loading = false )
      .subscribe(
        () => {
          this.mensagemAlerta.message('Dados atualizados com sucesso!');
        },
        (error: Error) => {
          this.mensagemAlerta.message(error.message);
        },() =>{
          this.router.navigate(['/pessoas']);
        }
      ); 
      
    }

  }

}
