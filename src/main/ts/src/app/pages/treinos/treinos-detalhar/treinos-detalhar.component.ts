import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TreinoService } from '../../../../generated/services';
import { Treino } from '../../../../generated/entities';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';

import { } from 'rxjs'

@Component({
  selector: 'app-treinos-detalhar',
  templateUrl: './treinos-detalhar.component.html',
  styleUrls: ['./treinos-detalhar.component.scss']
})
export class TreinosDetalharComponent implements OnInit {

  // para arpesentar ou não o Loader na tela
  loading = false;

  // objeto carregado na tela
  treino: Treino;

  constructor(
    private router: Router,
    private activedRoute: ActivatedRoute,
    private treinoService: TreinoService,
    private toast: MensagemAlertaService
  ) {
    
  }

  carregaDados(){

    this.loading = true;

    this.activedRoute.params
    .subscribe((paramns)=>{

      let parametroId: number = paramns.id;

      if(parametroId == null){
        this.loading = false;
        this.toast.errorFind('Não foi possível buscar o treino com o código informado');
        this.router.navigate(['treinos']);
        return;
      }

      this.treinoService.findTreinoById(parametroId)
        .finally(() => this.loading = false)
        .subscribe((treino: Treino)=>{
          this.treino = treino;
        });

    });
        
  }

  ngOnInit() {
    this.carregaDados();
  }

  editar(idTreino:number){
    this.router.navigate(['treinos/editar/', idTreino]);
  }

}
