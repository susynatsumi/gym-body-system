import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Pessoa, GeneroValues, PapelValues } from '../../../../generated/entities';
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

  constructor(
    private formBuilder: FormBuilder,
    private pessoaService: AccountService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    console.log('Passou');

    this.route.params.subscribe(
      paramns => this.parametroId = paramns.id
    );

    this.pessoa = {};

    if(this.parametroId != null){
      this.pessoaService.findPessoaById(this.parametroId).subscribe(
        (pessoa: Pessoa) => this.pessoa = pessoa
      );
    }

    this.formGroup = this.formBuilder.group({
      'nome': [this.pessoa.nome, Validators.compose([Validators.required, Validators.minLength(4)])],
      'email': [this.pessoa.email, Validators.compose([Validators.required, Validators.minLength(4)])],
      'login': [this.pessoa.login, Validators.compose([Validators.required, Validators.minLength(4)])],
      'senha': [this.pessoa.senha, Validators.compose([Validators.required, Validators.minLength(4)])],
      'genero': [this.pessoa.genero, Validators.required],
      'objetivo': [this.pessoa.objetivo],
      'dataNascimento': [this.pessoa.dataNascimento, Validators.required],
      'papel': [this.pessoa.papel, Validators.required]
    });

    // isAtivo

  }

  ngOnInit() {
    this.generos = GeneroValues;
    this.papeis = PapelValues;
  }

  salvar(){

    if(this.parametroId == null){

      this.pessoaService.insertPessoa(this.pessoa).subscribe((pessoa: Pessoa) => {
        console.log('Pessoa '+this.pessoa.nome+' Salva com id '+this.pessoa.id);
      });

    } else {

      this.pessoaService.updatePessoa(this.pessoa).subscribe(
        (pessoa: Pessoa) => {
          console.log('Pessoa '+this.pessoa.nome+ ' atualizada com sucesso '+this.pessoa.id);
        }
      ); 
      
    }

    this.router.navigateByUrl("/pessoas");

  }

}
