import { Component, OnInit } from '@angular/core';
import { GrupoMuscular } from '../../../../generated/entities';
import { GrupoMuscularService } from '../../../../generated/services';
import { ActivatedRoute, Router } from '../../../../../node_modules/@angular/router';

import 'rxjs/add/operator/finally';

@Component({
  selector: 'app-grupo-muscular-detalhar',
  templateUrl: './grupo-muscular-detalhar.component.html',
  styleUrls: ['./grupo-muscular-detalhar.component.scss']
})
export class GrupoMuscularDetalharComponent implements OnInit {

  // ---------------------------------------
  // --------------- ATRIBUTOS -------------
  // ---------------------------------------

  grupoMuscular: GrupoMuscular;
  loading = true;


  // ---------------------------------------
  // ------------- CONSTRUCTOR -------------
  // ---------------------------------------

  constructor(
    private grupoMuscularService: GrupoMuscularService,
    private route: ActivatedRoute,
    private router: Router
  ) { 
    
    this.route.params
      .subscribe(
        (paramns)=>{
          this.loading = true;
          console.log(paramns.id);
          this.grupoMuscularService.findGrupoMuscularById(paramns.id)
            .finally(() => this.loading = false)
            .subscribe((grupoMuscular: GrupoMuscular) =>{
              this.grupoMuscular = grupoMuscular;
              console.log(grupoMuscular.descricao);
            },(error: Error)=>{
              alert(error.message);
            });
        }
      );

  }


  // ----------------------------------------
  // ----------------- MÉTODOS --------------
  // ----------------------------------------

  ngOnInit() {
  }

  editar(id){
    this.router.navigate(['grupos-musculares/editar/', id]);
  }

}
