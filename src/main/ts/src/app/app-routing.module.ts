import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { PessoaFormComponent } from './pages/pessoas/pessoa-form/pessoa-form.component';
import { PessoasDetalharComponent } from './pages/pessoas/pessoas-detalhar/pessoas-detalhar.component';
import { GrupoMuscularFormComponent } from './pages/grupos-musculares/grupo-muscular-form/grupo-muscular-form.component';
import { GruposMuscularesComponent } from './pages/grupos-musculares/grupos-musculares.component';
import { GrupoMuscularDetalharComponent } from './pages/grupos-musculares/grupo-muscular-detalhar/grupo-muscular-detalhar.component';
import { ExerciciosComponent } from './pages/exercicios/exercicios.component';
import { ExerciciosFormComponent } from './pages/exercicios/exercicios-form/exercicios-form.component';
import { ExerciciosDetalharComponent } from './pages/exercicios/exercicios-detalhar/exercicios-detalhar.component';
import { EquipamentosComponent } from './pages/equipamentos/equipamentos.component';
import { EquipamentosFormComponent } from './pages/equipamentos/equipamentos-form/equipamentos-form.component';
import { EquipamentosDetalharComponent } from './pages/equipamentos/equipamentos-detalhar/equipamentos-detalhar.component';
import { TreinosComponent } from './pages/treinos/treinos.component';
import { TreinosFormComponent } from './pages/treinos/treinos-form/treinos-form.component';
import { TreinosDetalharComponent } from './pages/treinos/treinos-detalhar/treinos-detalhar.component';


const routes: Routes = [
  // pessoas
  {
    path: '', 
    redirectTo: 'pessoas',
    pathMatch: 'full'
  },
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
  // grupos musculares
  {
    path: 'grupos-musculares',
    component: GruposMuscularesComponent
  },
  {
    path: 'grupos-musculares/cadastrar',
    component: GrupoMuscularFormComponent
  },
  {
    path: 'grupos-musculares/editar/:id',
    component: GrupoMuscularFormComponent
  },
  {
    path: 'grupos-musculares/:id',
    component: GrupoMuscularDetalharComponent
  },
  // exercicios
  {
    path: 'exercicios',
    component: ExerciciosComponent
  },
  {
    path: 'exercicios/cadastrar',
    component: ExerciciosFormComponent
  },
  {
    path: 'exercicios/editar/:id',
    component: ExerciciosFormComponent
  },
  {
    path: 'exercicios/:id',
    component: ExerciciosDetalharComponent
  },
  // equipamentos
  {
    path: 'equipamentos',
    component: EquipamentosComponent
  },
  {
    path: 'equipamentos/cadastrar',
    component: EquipamentosFormComponent
  },
  {
    path: 'equipamentos/editar/:id',
    component: EquipamentosFormComponent
  },
  {
    path: 'equipamentos/:id',
    component: EquipamentosDetalharComponent
  },
  // treinos
  {
    path: 'treinos',
    component: TreinosComponent
  },
  {
    path: 'treinos/cadastrar',
    component: TreinosFormComponent
  },
  {
    path: 'treinos/editar/:id',
    component: TreinosFormComponent
  },
  {
    path: 'treinos/:id',
    component: TreinosDetalharComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
