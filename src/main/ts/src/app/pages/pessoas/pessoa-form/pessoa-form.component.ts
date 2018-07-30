import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { Pessoa, GeneroValues, PapelValues, Papel } from '../../../../generated/entities';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';
import { AccountService } from '../../../../generated/services';

import 'rxjs/add/operator/finally';

@Component({
  selector: 'pessoa-form',
  templateUrl: './pessoa-form.component.html',
  styleUrls: ['./pessoa-form.component.scss'],
})
export class PessoaFormComponent implements OnInit {

  // ---------------------------------------------
  // ---------------- ATRIBUTOS ------------------
  // ---------------------------------------------

  formStep1DadosPessoas: FormGroup;
  formStep2DadosAcesso: FormGroup;
  formStep3Permissoes: FormGroup;
  formStep4Objetivo: FormGroup;

  pessoa: Pessoa;
  generos: string[];
  papeis: string[];
  parametroId: number;
  pessoaLogada: Pessoa;

  public loading = false;

  // ---------------------------------------------
  // ---------------- CONSTRUCTOR ----------------
  // ---------------------------------------------

  constructor(
    private formBuilder: FormBuilder,
    private pessoaService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {

    this.pessoaLogada = JSON.parse(localStorage.getItem('usuarioLogado'));
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
              alert(error.message);
              this.router.navigate(['pessoas/cadastrar']);
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

  ngOnInit() {
    
    this.formStep1DadosPessoas = this.formBuilder.group({
      'nome': [this.pessoa.nome, Validators.compose([Validators.required, Validators.minLength(4)])],
      'genero': [this.pessoa.genero, Validators.required],
      'dataNascimento': [this.pessoa.dataNascimento, Validators.required],
    });

    this.formStep2DadosAcesso = this.formBuilder.group({
      'email': [this.pessoa.email, Validators.compose([Validators.required, Validators.minLength(4)])],
      'login': [this.pessoa.login],
      'senha': [this.pessoa.senha],
    });

    this.formStep3Permissoes = this.formBuilder.group({
      'papel': [this.pessoa.papel, Validators.required],
      'isAtivo': this.pessoa.isAtivo
    })

    this.formStep4Objetivo = this.formBuilder.group({
      'objetivo': [this.pessoa.objetivo],
    });

    this.generos = GeneroValues;
    this.papeis = PapelValues;

    if(this.pessoaLogada.papel != null && this.pessoaLogada.papel.toString() !=  'ADMINISTRATOR'  ){
      this.papeis = this.papeis.filter(papel => papel != 'ADMINISTRATOR');
    }
    
  }

  /**
   * Requisita ao servidor para salvar os dados do form
   */
  salvar(){

    if(this.parametroId == null){
      this.loading = false;
      this.pessoaService.insertPessoa(this.pessoa).subscribe((pessoa: Pessoa) => {
        //resultado
      },(error: Error)=>{
        //error
        alert(error.message);
      },() =>{
        this.router.navigate(['/pessoas']);
      }
    );

    } else {

      this.pessoaService.updatePessoa(this.pessoa).subscribe(
        () => {
        },
        (error: Error) => {
          alert(error.message);
        },() =>{
          this.router.navigate(['/pessoas']);
        }
      ); 
      
    }

  }

}
