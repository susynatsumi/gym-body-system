import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig, MatTableDataSource } from '@angular/material';

@Injectable()
export class RemoveRowTableService {

  removerLinhaTable(idRemover: number, dados: any[]){

    const itemIndex = dados.findIndex((academia) => academia.id === idRemover);

    dados.splice(itemIndex, 1);

    return new MatTableDataSource(dados);
  }
}
