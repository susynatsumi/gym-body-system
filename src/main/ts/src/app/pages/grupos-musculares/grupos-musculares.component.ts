import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, GrupoMuscular, PageRequest } from '../../../generated/entities';
import { GrupoMuscularService } from '../../../generated/services';
import { RemoveRowTableService } from '../../services/remove-row-table.service';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';

@Component({
  selector: 'app-grupos-musculares',
  templateUrl: './grupos-musculares.component.html',
  styleUrls: ['./grupos-musculares.component.scss']
})
export class GruposMuscularesComponent implements OnInit {

  // ---------------------------------------------
  // ----------------- ATRIBUTOS -----------------
  // ---------------------------------------------

  // evento que devolve um grupo muscular
  @Output() respostaDialog = new EventEmitter();

  // oculta alguns botões
  @Input() ocultarBotoes: Boolean = false;

  // grupos musculares que não devem mais ser apresentados
  @Input() codigosGruposMuscularesSelecionados: number[];

  // colunas da tabele
  colunas: string[] = ['nome', 'id', 'acoes'];
  // dada source da table
  dadosTable = new MatTableDataSource;

  // apresentar ou não o loader
  loading = false;

  // para ordenar e limitar a busca
  pageRequest: PageRequest;

  /**
   * 
   * @param grupoMuscularService 
   * @param removerRowService 
   * @param mensagemService 
   */
  constructor(
    private grupoMuscularService: GrupoMuscularService,
    private removerRowService: RemoveRowTableService,
    private mensagemService: MensagemAlertaService
  ) { 

    this.pageRequest = {
      page: 0,
      size: 100,
      sort: {
        orders: [
          {
            direction: 'ASC',
            nullHandlingHint: 'NATIVE',
            property: 'nome'
          }
        ]
      }
    };

  }

  /**
   * 
   */
  ngOnInit() {
    this.loading = true;
    this.listByfilters('');
  }

  /**
   * Faz uma busca no servidor de acordo com o filtro
   * @param filters 
   */
  listByfilters(filters: string){

    this.grupoMuscularService.listByFilters(
      filters, 
      this.getNotIN(), 
      this.pageRequest
    )
    .finally(() => this.loading = false )
      .subscribe(
        (gruposMusculares: Page<GrupoMuscular>) => {
          this.dadosTable.data = gruposMusculares.content;
        }
      );

  }

  /**
   * Seleciona um grupo muscular na dialog
   * @param element 
   */
  selecionar(element){
    this.respostaDialog.emit(element);
  }

  /**
   * Retorna os ids de grupos musculares que não devem mais ser apresentados
   */
  getNotIN(){

    if(
      this.codigosGruposMuscularesSelecionados != null 
      && this.codigosGruposMuscularesSelecionados.length > 0 
    ){
      return this.codigosGruposMuscularesSelecionados;
    }

    return [-19999];

  }

  /**
   * Remove um grupo muscular por id
   * @param id 
   */
  delete(id){
    this.loading = true;
    this.grupoMuscularService
      .deleteGrupoMuscular(id)
      .finally( () => this.loading = false )
      .subscribe(() =>{
        this.dadosTable = this.removerRowService
          .removerLinhaTable(
            id, 
            this.dadosTable.data
          );
          this.mensagemService.messageBottom('Grupo muscular removido com sucesso!');
      });
  }

}
