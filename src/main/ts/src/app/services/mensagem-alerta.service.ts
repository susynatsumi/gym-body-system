import { Injectable } from '@angular/core';
import { MatSnackBar } from '../../../node_modules/@angular/material';

@Injectable()
export class MensagemAlertaService {

  constructor(private snackBar: MatSnackBar) { }

  public errorSave(mensagem: string){
    
    this.snackBar.open(
      'Não foi possível salvar, pois ocorreu o seguinte erro: '+mensagem, 
      '', 
      {duration: 4000, verticalPosition: 'top' , horizontalPosition: 'center'}
    );

  }

  public errorFind(mensagem: string){
    
    this.snackBar.open(
      'O seguinte erro ocorreu ao carregar: '+mensagem, 
      '', 
      {duration: 4000, verticalPosition: 'top' , horizontalPosition: 'center'}
    );

  }

  public errorRemove(mensagem: string){
    
    this.snackBar.open(
      'O seguinte erro ocorreu ao remover: '+mensagem, 
      '', 
      {duration: 4000, verticalPosition: 'top' , horizontalPosition: 'center'}
    );

  }

  public message(mensagem: string){
    
    this.snackBar.open(
      mensagem, 
      '', 
      {duration: 4000, verticalPosition: 'top' , horizontalPosition: 'center'}
    );

  }

  public messageBottom(mensagem: string){
    this.snackBar.open(
      mensagem, 
      '', 
      {duration: 3000, verticalPosition: 'bottom' , horizontalPosition: 'center'}
    );

  }

}
