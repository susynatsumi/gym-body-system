import { Component, OnInit, Inject, Optional } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '../../../../../node_modules/@angular/material';
import { Equipamento } from '../../../../generated/entities';
import { EquipamentoService } from '../../../../generated/services';

@Component({
  selector: 'app-equipamento-dialog',
  templateUrl: './equipamento-dialog.component.html',
  styleUrls: ['./equipamento-dialog.component.scss'],
  providers: [EquipamentoService]
})
export class EquipamentoDialogComponent implements OnInit {

  ngOnInit() {
  }

  equipamentoSelecionado(event){
    console.log(event.target.value);
  }

  constructor(
    public dialogRef: MatDialogRef<EquipamentoDialogComponent>,
    @Optional() @Inject(MAT_DIALOG_DATA) public data: Equipamento,
    private equipamentoService: EquipamentoService
  ) {
    // console.log('safdsafas');
    // this.equipamentoService.findEquipamentoById(3).subscribe((equi: Equipamento)=>{
    //   console.log("sadfsafda");
    // }, (err: Error)=>{
    //   console.log('err');
    // })
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
   * @param equipamentoSelecinado
   * 
   */
  resposta(equipamentoSelecinado){
    this.dialogRef.close(equipamentoSelecinado);
  }


}
