import { Component, OnInit } from '@angular/core';
import { AccountService } from '../../../../generated/services';
import { Router, ActivatedRoute } from '../../../../../node_modules/@angular/router';
import { Pessoa } from '../../../../generated/entities';

@Component({
  selector: 'app-pessoas-detalhar',
  templateUrl: './pessoas-detalhar.component.html',
  styleUrls: ['./pessoas-detalhar.component.scss']
})
export class PessoasDetalharComponent implements OnInit {
  // ---------------------------------------------
  // ---------------- ATRIBUTOS ------------------
  // ---------------------------------------------
  pessoa: Pessoa;
  parametroId: number;
  loading = false;


  // --------------------------------------------
  // -----------------CONSTRUCTOR ---------------
  // --------------------------------------------
  constructor(
    private accountService: AccountService,
    private router: Router,
    private route: ActivatedRoute,
  ) {
    this.route.params.subscribe(
      paramns => {
        
        this.loading = true;

        this.parametroId = paramns.id;

        accountService.findPessoaById(this.parametroId)
          .finally( () => this.loading = false )
          .subscribe(
            (pessoa: Pessoa) =>{
              this.pessoa = pessoa;
            },(error: Error)=>{
              alert(error.message);
              router.navigate(['pessoas']);
            }
          );
      });
  }

  // ---------------------------------------------
  // ----------------- MÉTODOS -------------------
  // ---------------------------------------------

  ngOnInit() {
  }

  /**
   * Redireciona para a tela de edição
   * @param id 
   */
  editar(id){
    this.router.navigate(['pessoas/editar',id])
  }

}
