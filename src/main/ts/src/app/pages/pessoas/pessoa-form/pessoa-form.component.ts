import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { Pessoa, GeneroValues, PapelValues, Papel } from '../../../../generated/entities';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';
import { AccountService } from '../../../../generated/services';

@Component({
  selector: 'pessoa-form',
  templateUrl: './pessoa-form.component.html',
  styleUrls: ['./pessoa-form.component.scss'],
})
export class PessoaFormComponent implements OnInit {

  formGroup: FormGroup;
  pessoa: Pessoa;
  generos: string[];
  papeis: string[];
  parametroId: number;
  pessoaLogada: Pessoa;

  constructor(
    private formBuilder: FormBuilder,
    private pessoaService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    console.log('ada');
    this.pessoaLogada = JSON.parse(localStorage.getItem('usuarioLogado'));
    this.pessoa = {};

    this.route.params.subscribe(
      paramns => {
        this.parametroId = paramns.id

        if(this.parametroId != null){
          this.pessoaService.findPessoaById(this.parametroId).subscribe(
            (pessoa: Pessoa) => this.pessoa = pessoa
          );
        }else {
          this.pessoa.isAtivo = true;
        }
      });

  }

  ngOnInit() {
    
    this.formGroup = this.formBuilder.group({
      'nome': [this.pessoa.nome, Validators.compose([Validators.required, Validators.minLength(4)])],
      'email': [this.pessoa.email, Validators.compose([Validators.required, Validators.minLength(4)])],
      'login': [this.pessoa.login],
      'senha': [this.pessoa.senha],
      'genero': [this.pessoa.genero, Validators.required],
      'objetivo': [this.pessoa.objetivo],
      'dataNascimento': [this.pessoa.dataNascimento, Validators.required],
      'papel': [this.pessoa.papel, Validators.required],
      'isAtivo': this.pessoa.isAtivo
    });

    this.generos = GeneroValues;
    this.papeis = PapelValues;

    if(this.pessoaLogada.papel != null && this.pessoaLogada.papel.toString() !=  'ADMINISTRATOR'  ){
      console.log('if'+this.pessoaLogada.nome);
      this.papeis = this.papeis.filter(papel => papel != 'ADMINISTRATOR');
    }
    
  }

  salvar(){

    if(this.parametroId == null){

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
