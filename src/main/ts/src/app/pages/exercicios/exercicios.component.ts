import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '../../../../node_modules/@angular/material';
import { Page, Equipamento } from '../../../generated/entities';
import { Elementos } from '../../elementos/elementos';
import { EquipamentoService } from '../../../generated/services';

@Component({
  selector: 'app-exercicios',
  templateUrl: './exercicios.component.html',
  styleUrls: ['./exercicios.component.scss']
})
export class ExerciciosComponent implements OnInit {



  dadosTable = new MatTableDataSource;

  listaElementos: Page<any>;

  colunas: Elementos[];



  constructor(
    private equipamentoServic: EquipamentoService
  ) {
    this.colunas = [
      { descricaoColuna: "Descrição" , nomeColuna: "descricao" }
    ];

    this.equipamentoServic.listEquipamentoByFilters('')
      .subscribe(
        (equipamentos: Page<Equipamento>) =>{
          this.listaElementos = equipamentos;
          this.dadosTable = new MatTableDataSource(this.listaElementos.content);
          console.log(this.listaElementos.numberOfElements);
        }
      );


   }

  ngOnInit() {
    
  }

}
