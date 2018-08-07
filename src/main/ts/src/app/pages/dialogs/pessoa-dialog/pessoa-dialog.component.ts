import { Component, OnInit, Optional, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '../../../../../node_modules/@angular/material';
import { Pessoa } from '../../../../generated/entities';

@Component({
  selector: 'app-pessoa-dialog',
  templateUrl: './pessoa-dialog.component.html',
  styleUrls: ['./pessoa-dialog.component.scss']
})
export class PessoaDialogComponent implements OnInit {


  // ----------------------------------------------
  // --------------- CONSTROCTOR ------------------
  // ----------------------------------------------
  
  /**
   * 
   * @param dialogRef 
   * @param data 
   */
  constructor(
    public dialogRef: MatDialogRef<PessoaDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Pessoa,
  ) {
  }

  /**
   * 
   */
  ngOnInit(){
  }

  /**
   * Fechar sem selecinar nada
   */
  onNoClick() {
    this.dialogRef.close();
  }

  /**
   * 
   * Devolve para o componente que chama o valor selecionado
   * 
   * @param selecinado
   * 
   */
  resposta(selecinado){
    this.dialogRef.close(selecinado);
  }

}
