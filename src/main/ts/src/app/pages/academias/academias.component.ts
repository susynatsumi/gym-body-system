import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, Academia } from '../../../generated/entities';
import { Router } from '../../../../node_modules/@angular/router';
import { AcademiaService } from '../../../generated/services';

@Component({
  selector: 'app-academias',
  templateUrl: './academias.component.html',
  styleUrls: ['./academias.component.scss']
})
export class AcademiasComponent implements OnInit {

  colunas: string[] = ['razaoSocial', 'cidade', 'telefone', 'id', 'acoes'];
  dadosTable = new MatTableDataSource();
  academiasTable:  Page<Academia>;

  constructor(
    private router: Router,
    private academiaService: AcademiaService
  ) { }

  ngOnInit() {
    this.listByfilters('');
  }

  listByfilters(filters: string){
    this.academiaService.listAcademiaByFilters(filters)
      .subscribe(
        (academias: Page<Academia>) => {
          this.academiasTable  = academias;
          this.dadosTable = new MatTableDataSource(academias.content)
        },(error: Error)=>{
          console.log('Error'+ error.message);
        }
      );
  }


  delete(id){
    this.academiaService.deleteAcademia(id).subscribe(()=>{
      this.removerLinhaTable(id);
    },(error: Error)=>{
      alert('Ocorreu um erro ao deletar');
    });

  }

  removerLinhaTable(id){
    const itemIndex = this.academiasTable.content.findIndex(academia => academia.id === id);
    this.academiasTable.content.splice(itemIndex, 1);
    this.dadosTable = new MatTableDataSource(this.academiasTable.content);
  }

  filtrar(filtro: string) {
    console.log(filtro);
    this.dadosTable.filter = filtro.trim().toLowerCase();
  }


}
