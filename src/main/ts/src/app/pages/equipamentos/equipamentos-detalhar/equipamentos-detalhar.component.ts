import { Component, OnInit } from '@angular/core';

import 'rxjs/add/operator/finally';

import { Equipamento } from '../../../../generated/entities';
import { Router, ActivatedRoute } from '@angular/router';
import { EquipamentoService } from '../../../../generated/services';
import { MensagemAlertaService } from '../../../services/mensagem-alerta.service';

@Component({
  selector: 'app-equipamentos-detalhar',
  templateUrl: './equipamentos-detalhar.component.html',
  styleUrls: ['./equipamentos-detalhar.component.scss']
})
export class EquipamentosDetalharComponent implements OnInit {

  // -----------------------------------
  // -------------- ATRIBUTOS ----------
  // -----------------------------------

  // equipamento apresentado na tela
  equipamento: Equipamento;

  // parametro passado na url
  parametroId: number;

  // apresetar ou não o loader na tela
  loading = true;

  // imagem default
  imageUrl: string = "/assets/imagens/imagem-default.png";

  // -----------------------------------
  // ----- CONSTRUCTOR -----------------
  // -----------------------------------

  /**
   * 
   * @param academiaService 
   * @param router 
   * @param route 
   */
  constructor(
    private equipamentoService: EquipamentoService,
    private mensagemService: MensagemAlertaService,
    private router: Router,
    private route: ActivatedRoute,
  ) { 

    this.route.params.subscribe(
      paramns => {
        this.parametroId = paramns.id;

        equipamentoService.findEquipamentoById(this.parametroId)
          .finally( () => this.loading = false )
          .subscribe(
            (equipamento: Equipamento) =>{
              this.equipamento = equipamento;
            },(error: Error)=>{
              mensagemService.errorFind(error.message);
              router.navigate(['equipamentos']);
            }
          );
      });
  }

  /**
   * 
   */
  ngOnInit() {
  }

  /**
   * Redireciona para a página de edição
   * @param id 
   */
  editar(id){
    this.router.navigate(['equipamentos/editar',id]);
  }

}
