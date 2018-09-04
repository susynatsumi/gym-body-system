import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Equipamento } from '../../../../generated/entities';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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

  novaFoto: File;
  imageUrl: string = "/assets/imagens/imagem-default.png";

  equipamento: Equipamento;

  formEquipamento: FormGroup;
  formImagem: FormGroup;

  parametroId: number;
  loading = true;

  // ---------------------------------
  // ----------  CONSTRUCTOR   -------
  // ---------------------------------

  constructor(
    private formBuilder: FormBuilder,
    private activedRoute: ActivatedRoute,
    private router: Router,
    private equipamentoService: EquipamentoService,
  ) { 
    this.equipamento = {};
    this.loading = true;

    this.carregaEquipamento();

  }

  // --------------------------------------
  // ---------------- METODOS -------------
  // --------------------------------------

  /**
   * Busca os dados do equipamento por id
   */
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

            console.log(equipamento.imagem);

          },(error: Error)=>{
            alert(error.message);
          });
      });
  }

  /**
   * 
   */
  ngOnInit() {

    this.formImagem = this.formBuilder.group({
      imagemFileTransfer: [this.equipamento.imagemFileTransfer, Validators.required]
    });

    this.formEquipamento = this.formBuilder.group({
      'descricao': [this.equipamento.descricao, Validators.required],
      'isAtivo': [this.equipamento.isAtivo]
    });
    
    if(this.parametroId == null){
      this.formEquipamento.get('isAtivo').disable();
    } else {
      this.formEquipamento.get('isAtivo').enable();
    }

  }

  /**
   * Remove o equipamento da tela
   */
  removerImagem(){
    this.equipamento.imagem = null;
    this.equipamento.imagemFileTransfer = null;
    this.novaFoto = null;
    this.imageUrl =  "/assets/imagens/imagem-default.png";
  }

  /**
   * Seleciona a imagem e altera a apresentada no compenent img
   * @param event 
   */
  setArquivo(event) {
    
    this.removerImagem();

    if (event.target.files[0]){
      this.equipamento.imagemFileTransfer = event.target;

      this.novaFoto = event.target.files.item(0);
  
      //Show image preview
      var reader = new FileReader();
      reader.onload = (event:any) => {
        this.imageUrl = event.target.result;
      }
  
      reader.readAsDataURL(this.novaFoto);

    }
   
  }
 /*  setArquivo(event: any){
    this.loading = true;
    if (event.target.files[0]){
      this.imagemService.convertToByteArray(event.target)
        .subscribe(imagem => {
          this.loading = false;
          // console.log(imagem);
          this.equipamento.imagem = imagem;
        });
      // this.equipamento.imagemFileTransfer = event.target
    }
  } */

  /* onUpload(event){
    const reader = new FileReader();
 
    if(event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
  
      reader.onload = () => {
        this.formImagem.patchValue({
          imagemFileTransfer: reader.result
       });
      
        // need to run CD since file load runs outside of zone
        this.changeDetector.markForCheck();
      };
    }

    // console.log(this.formImagem.get('file').value);

  } */

  /**
   * Salva os dados do equipamento
   */
  salvar(){

    this.loading = true;
    this.equipamento.imagem = null;
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

  /**
   * Envia os dados do equipamento para o servidor
   */
  enviar(){

    if( this.parametroId == null ){
      return this.equipamentoService
        .insertEquipamento(this.equipamento);
    } else {
      return this.equipamentoService
        .updateEquipamento(this.equipamento);
    }

  }

}
