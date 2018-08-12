import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { Page, Sort, PageRequest, Equipamento, SortDirection} from '../../../generated/entities';
import { EquipamentoService } from '../../../generated/services';
import { MensagemAlertaService } from '../../services/mensagem-alerta.service';
import { RemoveRowTableService } from '../../services/remove-row-table.service';

@Component({
  selector: 'app-equipamentos',
  templateUrl: './equipamentos.component.html',
  styleUrls: ['./equipamentos.component.scss'],
  providers: [EquipamentoService]
})
export class EquipamentosComponent implements OnInit {

  // ---------------------------------------------
  // ----------------- ATRIBUTOS -----------------
  // ---------------------------------------------

  // resposta para a dialog
  @Output() respostaDialog = new EventEmitter();

  // para ocultar alguns botoes
  @Input() ocultarBotoes: Boolean = false;

  // para ordenacao e limite na busca
  pageRequest: PageRequest;

  // colunas da table
  colunas: string[] = ['descricao', 'id', 'acoes'];

  // dados da tabela
  dadosTable = new MatTableDataSource;

  // para apresentar ou nao o loader na tela
  loading = false;

  /**
   * 
   * @param equipamentoService 
   * @param messageService 
   * @param removerRow 
   */
  constructor(
    private equipamentoService: EquipamentoService,
    private messageService: MensagemAlertaService,
    private removerRow: RemoveRowTableService
  ) { 
    this.pageRequest = {
      page: 0,
      size: 100,
      sort: {
        orders: [
          {
            direction: 'ASC',
            nullHandlingHint: 'NATIVE',
            property: 'descricao'
          }
        ]
      }
    };
  }

  /**
   * Primeira ação
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
    
    this.equipamentoService.listEquipamentoByFilters(
      filters,
      null, 
      this.pageRequest
    )
    .finally(() => this.loading = false )
      .subscribe(
        (equipamentos: Page<Equipamento>) => {
          this.dadosTable.data = equipamentos.content;
        }
      );
  }

  /**
   * Remove equipamento
   * @param id 
   */
  delete(id){

    this.loading = true;

    this.equipamentoService.deleteEquipamento(id)
    .finally(() => this.loading = false )
    .subscribe(()=>{
      this.dadosTable= this
        .removerRow
        .removerLinhaTable(
          id, 
          this.dadosTable.data
        );

        this.messageService.messageBottom('Equipamento removido com sucesso!');

    },(error: Error)=>{

      this.messageService.errorRemove(error.message);

    });

  }

  /**
   * Seleciona um item na dialog
   * @param element
   */
  selecionar(element){
    this.respostaDialog.emit(element);
  }

}
