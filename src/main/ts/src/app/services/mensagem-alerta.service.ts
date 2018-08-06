import { Injectable } from '@angular/core';
import { MatSnackBar, MatSnackBarConfig } from '../../../node_modules/@angular/material';
import { Component } from '../../../node_modules/@angular/compiler/src/core';

@Injectable()
export class MensagemAlertaService {

  constructor(public snackBar: MatSnackBar) { }

  // let mat: MatSnackBarConfig

  public errorSave(mensagem: string){
    
    this.snackBar.open(
      'Não foi possível salvar, pois ocorreu o seguinte erro: '+mensagem, 
      '', 
      {duration: 5000, verticalPosition: 'top' , horizontalPosition: 'center'}
    );

  }

  public errorFind(mensagem: string){
    
    this.snackBar.open(
      'O seguinte erro ocorreu ao carregar: '+mensagem, 
      '', 
      {duration: 5000, verticalPosition: 'top' , horizontalPosition: 'center'}
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
      {duration: 5000, verticalPosition: 'top' , horizontalPosition: 'center'}
    );

  }

}
