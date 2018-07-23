import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { PessoaFormComponent } from './pages/pessoas/pessoa-form/pessoa-form.component';
import { AcademiasFormComponent } from './pages/academias/academias-form/academias-form.component';
import { AcademiasComponent } from './pages/academias/academias.component';


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
    path: 'pessoas/:id',
    component: PessoaFormComponent
  },
  {
    path: 'academias',
    component: AcademiasComponent
  },
  {
    path: 'academias/:id',
    component: AcademiasFormComponent
  },
  {
    path: 'academias/cadastrar',
    component: AcademiasFormComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
