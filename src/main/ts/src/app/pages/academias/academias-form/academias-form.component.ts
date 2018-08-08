import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '../../../../../node_modules/@angular/forms';
import { AcademiaService, AccountService } from '../../../../generated/services';
import { Academia, Pessoa, Page } from '../../../../generated/entities';

import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';

import 'rxjs/add/operator/finally';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';

@Component({
  selector: 'app-academias-form',
  templateUrl: './academias-form.component.html',
  styleUrls: ['./academias-form.component.scss']
})
export class AcademiasFormComponent implements OnInit {

  // ---------------------------------------------
  // ---------------- ATRIBUTOS ------------------
  // ---------------------------------------------

  // objeto populado no form
  public academia: Academia;

  // form groups
  public formStep1DadosAcademia: FormGroup;
  public formStep2Proprietario: FormGroup;
  public formStep3DadosEndereco: FormGroup;
  // fim form groups

  // form control
  // public pessoaControl: FormControl;

  // public filteredPessoas: Pessoa[];
  public pessoasList: Pessoa[];

  // public pessoas: Page<Pessoa>;

  // para apresentar ou não o loader
  loading = false;

  // parametro passado em caso de edição
  parametroId: number;

  // ---------------------------------------------
  // ---------------- CONSTRUCTOR ----------------
  // ---------------------------------------------

  /**
   * Constroctor 
   */
  constructor(
    private formBuilder: FormBuilder,
    private academiaService: AcademiaService,
    private pessoaService: AccountService,
    private route: ActivatedRoute,
    private router: Router,
    private mensagemAlertaService: MensagemAlertaService,
  ) { 

    this.academia = {
      pessoaProprietario: {
        nome:''
      },
      isAtiva: true
    };

    this.route.params.subscribe(
      paramns => {

        if(paramns.id != null){

          this.loading = true;

          this.parametroId = paramns.id;

          this.academiaService.findAcdemiaById(this.parametroId)
            .finally( () => this.loading = false )
            .subscribe(
              (academia: Academia) => {
                this.academia = academia
              },(error: Error) => {
                this.mensagemAlertaService.errorFind(error.message);
                this.router.navigate(['academias/cadastrar']);
              }
            );
        }
      });
  }

  /**
   * On Init
   */
  ngOnInit() {

    this.formStep1DadosAcademia = this.formBuilder.group({
      'razaoSocial': [this.academia.razaoSocial, Validators.compose([Validators.required, Validators.minLength(4)])],
      'nomeFantasia': [this.academia.nomeFantasia, Validators.compose([Validators.required, Validators.minLength(4)])],
      'cnpj': [this.academia.cnpj, Validators.compose([Validators.required, Validators.minLength(14),  Validators.maxLength(14)])],
      'isAtiva': [this.academia.isAtiva],
      'telefone': [this.academia.telefone, Validators.compose([Validators.required, Validators.minLength(10),  Validators.maxLength(11)])],
    });

    this.formStep2Proprietario = this.formBuilder.group({
      'pessoaProprietario': [this.academia.pessoaProprietario, Validators.required],
    });

    this.formStep3DadosEndereco = this.formBuilder.group({
      'endereco': [this.academia.endereco, Validators.compose([Validators.required, Validators.maxLength(80)])],
      'cep': [this.academia.cep, Validators.compose([Validators.required, Validators.minLength(8),  Validators.maxLength(8)])],
      'cidade': [this.academia.cidade, Validators.compose([Validators.required, Validators.minLength(5)])],
    });

    // this.pessoaControl = new FormControl();

    // this.listaPessoasAtivas().subscribe((pessoas: Page<Pessoa>) => {
    //   this.pessoas = pessoas;
      // this.filteredPessoas = this.pessoaControl.valueChanges
      // .pipe(
      //   startWith(''),
      //   map(pessoa => pessoa ? 
      //       this.filterPessoas(pessoa) 
      //     : 
      //       this.pessoas.content.slice()
      //   )
      //);
    // });

  }

  /**
   * Salva os dados de uma academia de acordo com o formulário
   */
  salvar(){
    
    this.loading = true;

    this.enviar().finally( ()=> this.loading = false )
    .subscribe(
      () =>{
      },(error: Error)=>{
        alert(error.message);
      },() =>{
        this.router.navigate(['/academias']);
      }
    );
  }

  /**
   * Envia os dados para o servidor
   */
  enviar(){

    if(this.parametroId == null ){
      return this.academiaService.insertAcademia(this.academia);
    } else {
      return this.academiaService.updateAcademia(this.academia);
    }

  }


  /**
   * Lista pessoas para o campo proprietário
   */
  // listaPessoasAtivas(){
  //   return this.pessoaService.listByFilters('');
  // }

  /**
   * Filtra pessoas carregadas no autocomplete
   * @param name 
   */
  // filterPessoas(name: string) {

  //   return this.pessoas.content.filter(pessoa =>
  //       pessoa.nome.toLowerCase().indexOf(name.toLowerCase()) === 0);

  // }

  setProprietario( academia: Academia, pessoa: Pessoa ){
    if(pessoa){
      academia.pessoaProprietario = pessoa;
    }
  }

  listPessoasByFilters(filter: string){
    this.pessoaService.listByFilters(filter).subscribe((pessoas: Page<Pessoa>) => {
      this.pessoasList = pessoas.content;
    });
  }


}
