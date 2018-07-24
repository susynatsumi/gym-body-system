import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { PessoaFormComponent } from './pages/pessoas/pessoa-form/pessoa-form.component';
import { AcademiasFormComponent } from './pages/academias/academias-form/academias-form.component';
import { AcademiasComponent } from './pages/academias/academias.component';
import { AcademiaDetalharComponent } from './pages/academias/academia-detalhar/academia-detalhar.component';
import { PessoasDetalharComponent } from './pages/pessoas/pessoas-detalhar/pessoas-detalhar.component';


const routes: Routes = [
  {
    path: 'pessoas', 
    component: PessoasComponent,
    // children: [{
    //   path: 'cadastrar',
    //   component: PessoaFormComponent
    // }
    // ]
  },
  {
    path: 'pessoas/cadastrar',
    component: PessoaFormComponent
  },
  {
    path: 'pessoas/editar/:id',
    component: PessoaFormComponent
  },
  {
    path: 'pessoas/:id',
    component: PessoasDetalharComponent
  },
  {
    path: 'academias',
    component: AcademiasComponent
  },
  {
    path: 'academias/cadastrar',
    component: AcademiasFormComponent
  },
  {
    path: 'academias/editar/:id',
    component: AcademiasFormComponent
  },
  {
    path: 'academias/:id',
    component: AcademiaDetalharComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
