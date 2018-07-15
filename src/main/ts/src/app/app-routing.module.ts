import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { PessoaFormComponent } from './pages/pessoas/pessoa-form/pessoa-form.component';


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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
