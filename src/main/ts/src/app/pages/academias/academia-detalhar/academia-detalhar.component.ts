import { Component, OnInit } from '@angular/core';
import { Academia } from '../../../../generated/entities';
import { AcademiaService } from '../../../../generated/services';
import { Route, Router, ActivatedRoute } from '../../../../../node_modules/@angular/router';

import 'rxjs/add/operator/finally';

@Component({
  selector: 'app-academia-detalhar',
  templateUrl: './academia-detalhar.component.html',
  styleUrls: ['./academia-detalhar.component.scss']
})
export class AcademiaDetalharComponent implements OnInit {

  // -----------------------------------
  // -------------- ATRIBUTOS ----------
  // -----------------------------------

  academia: Academia;
  parametroId: number;

  loading = true;

  // -----------------------------------
  // ----- CONSTRUCTOR -----------------
  // -----------------------------------

  /**
   * 
   * @param academiaService 
   * @param router 
   * @param route 
   */
  constructor(
    private academiaService: AcademiaService,
    private router: Router,
    private route: ActivatedRoute,
  ) { 

    this.route.params.subscribe(
      paramns => {
        this.parametroId = paramns.id;

        academiaService.findAcdemiaById(this.parametroId)
          .finally( () => this.loading = false )
          .subscribe(
            (academia: Academia) =>{
              this.academia = academia;
            },(error: Error)=>{
              alert(error.message);
              router.navigate(['academias']);
            }
          );
      });
  }

  /**
   * 
   */
  ngOnInit() {
  }

  /**
   * Redireciona para a página de edição
   * @param id 
   */
  editar(id){
    this.router.navigate(['academias/editar',id]);
  }

}
