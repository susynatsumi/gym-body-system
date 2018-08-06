import { Component, OnInit } from '@angular/core';
import { GrupoMuscular } from '../../../../generated/entities';
import { FormGroup, FormBuilder, Validators } from '../../../../../node_modules/@angular/forms';
import { GrupoMuscularService } from '../../../../generated/services';

import 'rxjs/add/operator/finally';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';

@Component({
  selector: 'app-grupo-muscular-form',
  templateUrl: './grupo-muscular-form.component.html',
  styleUrls: ['./grupo-muscular-form.component.scss']
})
export class GrupoMuscularFormComponent implements OnInit {

  // -------------------------------------
  // --------------ATRIBUTOS -------------
  // -------------------------------------

  grupoMuscular: GrupoMuscular;

  formGrupoMusular: FormGroup;

  loading: any = false;

  private parametroId: number;

  // -------------------------------------
  // --------------CONTRUCTOR ------------
  // -------------------------------------

  /**
   * 
   * @param formBuilder 
   * @param grupoMuscularService 
   * @param router 
   * @param route 
   */
  constructor(
    private formBuilder: FormBuilder,
    private grupoMuscularService: GrupoMuscularService,
    private router: Router,
    private route: ActivatedRoute
  ) { 
    this.grupoMuscular = {};

    this.route.params
    .subscribe(
      (paramns)=>{

        this.parametroId = paramns.id;

        if(this.parametroId != null){

          this.loading = true;

          this.grupoMuscularService.findGrupoMuscularById(paramns.id)
            .finally(() => this.loading = false)
            .subscribe((grupoMuscular: GrupoMuscular) =>{
              this.grupoMuscular = grupoMuscular;
            },(error: Error)=>{
              alert(error.message);
            });

          }
      }
    );

  }

  // -------------------------------------
  // -------------- MÉTODOS  -------------
  // -------------------------------------

  /**
   * 
   */
  ngOnInit() {

    this.formGrupoMusular = this.formBuilder.group({
      'nome': [this.grupoMuscular.nome, Validators.compose([Validators.required, Validators.minLength(4)])],
      'descricao': [this.grupoMuscular.descricao],
    });

  }

  /**
   * Salva os dados no servidor
   */
  salvar(){
    
    this.loading = true;

    this.enviar().finally(() => this.loading = false)
      .subscribe(
        ()=>{
          this.router.navigate(['grupos-musculares']);
        },
        (error: Error)=>{
          alert(error.message);
        }
      );
   
  }

  /**
   * Envia os dados para o servidor
   */
  enviar(){

    if(this.parametroId == null){
      return this.grupoMuscularService.insertGrupoMuscular(
        this.grupoMuscular
      );
    } else {
      return this.grupoMuscularService.updateGrupoMuscular(
        this.grupoMuscular
      );
    }
  }

}
