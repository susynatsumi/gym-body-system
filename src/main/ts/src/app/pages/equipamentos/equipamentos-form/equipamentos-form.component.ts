import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Equipamento } from '../../../../generated/entities';
import { FormGroup, FormBuilder, Validators } from '../../../../../node_modules/@angular/forms';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';
import { EquipamentoService } from '../../../../generated/services';

import 'rxjs/add/operator/finally';

@Component({
  selector: 'app-equipamentos-form',
  templateUrl: './equipamentos-form.component.html',
  styleUrls: ['./equipamentos-form.component.scss']
})
export class EquipamentosFormComponent implements OnInit {

  // ---------------------------------
  // ----------   ATRIBUTOS ----------
  // ---------------------------------

  equipamento: Equipamento;

  formEquipamento: FormGroup;
  formImagem: FormGroup;

  parametroId: number;

  loading = true;

  imagemSelecionada = null;

  // ---------------------------------
  // ----------  CONSTRUCTOR   -------
  // ---------------------------------

  constructor(
    private formBuilder: FormBuilder,
    private activedRoute: ActivatedRoute,
    private router: Router,
    private equipamentoService: EquipamentoService,
    private changeDetector: ChangeDetectorRef
  ) { 
    this.equipamento = {};
    this.loading = true;

    this.carregaEquipamento();

  }

  carregaEquipamento(){

    this.activedRoute.params
      .subscribe((parametro) =>{
        this.loading = true;
        this.parametroId = parametro.id;
        
        if(this.parametroId == null){
          this.equipamento.isAtivo = true;
          this.loading = false;
          return;
        }

        this.equipamentoService.findEquipamentoById(this.parametroId)
          .finally(()=> this.loading = false)
          .subscribe( (equipamento: Equipamento) => {
            this.equipamento = equipamento;
          },(error: Error)=>{
            alert(error.message);
          });
      });
  }

  //-----------------------------------
  //-------------- MÉTODOS ------------
  //-----------------------------------

  ngOnInit() {

    this.formImagem = this.formBuilder.group({
      imagem: [null, Validators.required]
    });

    this.formEquipamento = this.formBuilder.group({
      'descricao': [this.equipamento.descricao, Validators.required],
      'isAtivo': [this.equipamento.isAtivo]
    });
    
  }

  onUpload(event){
    const reader = new FileReader();
 
    if(event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
  
      reader.onload = () => {
        this.formImagem.patchValue({
          imagem: reader.result
       });
      
        // need to run CD since file load runs outside of zone
        this.changeDetector.markForCheck();
      };
    }

    // console.log(this.formImagem.get('file').value);

  }

  salvar(){

    console.log(this.equipamento.imagem);

    // this.equipamento.imagem = this.formImagem.get('imagem').value;  

    this.enviar()
    .finally(()=> this.loading = false)
    .subscribe(
      ()=>{
        this.router.navigate(['equipamentos']);
      },
      (error: Error)=>{
        alert(error.message);
      }
    )

  }

  enviar(){

    console.log(this.equipamento.imagem)

    if( this.parametroId == null ){
      return this.equipamentoService
        .insertEquipamento(this.equipamento);
    } else {
      return this.equipamentoService
        .updateEquipamento(this.equipamento);
    }

  }

}
