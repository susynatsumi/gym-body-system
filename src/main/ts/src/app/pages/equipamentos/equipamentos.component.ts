import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, Sort, PageRequest, Equipamento, SortDirection} from '../../../generated/entities';
import { EquipamentoService } from '../../../generated/services';

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

  @Output() respostaDialog = new EventEmitter();

  @Input() ocultarBotoes: Boolean = false;

  sort: Sort;
  pageRequest: PageRequest;
  sortDirection: SortDirection;

  colunas: string[] = ['descricao', 'id', 'acoes'];

  dadosTable = new MatTableDataSource;
  equipamentosTable:  Page<Equipamento>;
  loading = false;

  constructor(
    private equipamentoService: EquipamentoService,
  ) { 
    console.log(equipamentoService);
    console.log(this.ocultarBotoes);
    /* this.sort =  {
      orders: [{
        property: 'descricao',
        direction: 'ASC'
      }]
    };

    console.log(this.sort);

    this.pageRequest = {
      page: 0,
      size: 100,
      sort: this.sort
    };
 */
  }

  ngOnInit() {
    this.listByfilters('');
  }

   /**
   * Filtra os dados já caregados na table
   * @param filtro
   */
  filtrar(filtro: string) {
    console.log(filtro);
    this.dadosTable.filter = filtro.trim().toLowerCase();
  }

  /**
   * Faz uma busca no servidor de acordo com o filtro
   * @param filters 
   */
  listByfilters(filters: string){
    
    this.loading = true;

    this.equipamentoService.listEquipamentoByFilters(
      filters
      // , 
      // null, 
      // this.pageRequest
    )
    .finally(() => this.loading = false )
      .subscribe(
        (equipamentos: Page<Equipamento>) => {
          this.equipamentosTable = equipamentos;
          this.dadosTable = new MatTableDataSource(equipamentos.content)
        }
      );
  }

  /**
   * Remove equipamento
   * @param id 
   */
  delete(id){
    console.log(id);
    console.log('chamdou delete');
    this.equipamentoService.deleteEquipamento(id).subscribe(()=>{
      this.removerLinhaTable(id);
      console.log('remove');
    },(error: Error)=>{
      alert('Ocorreu um erro ao deletar');
    });

  }

  /**
   * Remove a linha da tabela em que foi solicitada a remoção
   * @param id 
   */
  removerLinhaTable(id){
    
    console.log('sdljsalkfjsalkçfjasklçjfajfjjklasjklsadklçfdsajlkçsfadjklfsajklçfas');

    const itemIndex = this.equipamentosTable.content
    .findIndex(
      equipamento => equipamento.id === id
    );

    this.equipamentosTable.content.splice(itemIndex, 1);

    this.dadosTable = new MatTableDataSource(
      this.equipamentosTable.content
    );

  }

  selecionar(element){
    this.respostaDialog.emit(element);
  }

}
