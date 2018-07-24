import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, Academia } from '../../../generated/entities';
import { Router } from '../../../../node_modules/@angular/router';
import { AcademiaService } from '../../../generated/services';

import 'rxjs/add/operator/finally';

@Component({
  selector: 'app-academias',
  templateUrl: './academias.component.html',
  styleUrls: ['./academias.component.scss']
})
export class AcademiasComponent implements OnInit {

  // ---------------------------------------------
  // ---------------- ATRIBUTOS ------------------
  // ---------------------------------------------

  colunas: string[] = ['razaoSocial', 'cidade', 'telefone', 'id', 'acoes'];
  dadosTable = new MatTableDataSource();
  academiasTable:  Page<Academia>;

  loading = false;

  // ---------------------------------------------
  // ---------------- CONSTRUCTOR ----------------
  // ---------------------------------------------

  constructor(
    private router: Router,
    private academiaService: AcademiaService
  ) { }

  // ---------------------------------------------
  // ---------------- MÉTODOS   ------------------
  // ---------------------------------------------

  ngOnInit() {
    this.listByfilters('');
  }

  /**
   * Faz requisição ao servidor de acordo com o filtro
   * @param filters 
   */
  listByfilters(filters: string){
    this.loading = true;
    this.academiaService.listAcademiaByFilters(filters)
      .finally( () => this.loading = false )
      .subscribe(
        (academias: Page<Academia>) => {
          this.academiasTable  = academias;
          this.dadosTable = new MatTableDataSource(academias.content)
        },(error: Error)=>{
          console.log('Error'+ error.message);
        }
      );
  }

  /**
   * Delete academia de acordo com o id
   * @param id 
   */
  delete(id){
    this.academiaService.deleteAcademia(id)
    .subscribe(()=>{
      this.removerLinhaTable(id);
    },(error: Error)=>{
      alert('Ocorreu um erro ao deletar');
    });

  }

  /**
   * Remove a linha da tabela 
   * @param id 
   */
  removerLinhaTable(id){
    const itemIndex = this.academiasTable.content.findIndex(academia => academia.id === id);
    this.academiasTable.content.splice(itemIndex, 1);
    this.dadosTable = new MatTableDataSource(this.academiasTable.content);
  }

  /**
   * Filtra os dados já carregados na table
   * @param filtro 
   */
  filtrar(filtro: string) {
    console.log(filtro);
    this.dadosTable.filter = filtro.trim().toLowerCase();
  }


}
