import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, GrupoMuscular } from '../../../generated/entities';
import { GrupoMuscularService } from '../../../generated/services';

@Component({
  selector: 'app-grupos-musculares',
  templateUrl: './grupos-musculares.component.html',
  styleUrls: ['./grupos-musculares.component.scss']
})
export class GruposMuscularesComponent implements OnInit {

  // ---------------------------------------------
  // ----------------- ATRIBUTOS -----------------
  // ---------------------------------------------

  @Output() respostaDialog = new EventEmitter();

  @Input() ocultarBotoes: Boolean = false;

  colunas: string[] = ['nome', 'id', 'acoes'];
  dadosTable = new MatTableDataSource;
  gruposMuscularesTable:  Page<GrupoMuscular>;
  loading = false;

  constructor(
    private grupoMuscularService: GrupoMuscularService
  ) { }

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
    this.loading= true;
    this.grupoMuscularService.listByFilters(filters)
    .finally(() => this.loading = false )
      .subscribe(
        (gruposMusculares: Page<GrupoMuscular>) => {
          this.gruposMuscularesTable = gruposMusculares;
          this.dadosTable = new MatTableDataSource(gruposMusculares.content)
        }
      );
  }

  selecionar(element){
    this.respostaDialog.emit(element);
  }

}
