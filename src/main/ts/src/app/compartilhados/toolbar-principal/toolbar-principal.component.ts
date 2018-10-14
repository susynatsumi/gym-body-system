import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'toolbar-principal',
  templateUrl: './toolbar-principal.component.html',
  styleUrls: ['./toolbar-principal.component.scss']
})
export class ToolbarPrincipalComponent implements OnInit {


  logo =  '../../../assets/imagens/logo3.png';

  constructor(
    private http: HttpClient
  ) { }

  ngOnInit() {
  }

  // logout(){
  //   console.log('logout');
  //   this.http.get('/logout');
  // }

}
