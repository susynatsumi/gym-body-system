// angular imports 
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FlexLayoutModule  } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// fim angular imports

// date time picker
import { OwlDateTimeModule, OwlNativeDateTimeModule, OWL_DATE_TIME_LOCALE, OwlDateTimeIntl } from 'ng-pick-datetime';

// mask para campos
import { NgxMaskModule } from 'ngx-mask';

// loader
import { LoadingModule } from 'ngx-loading';

//outros imports
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { GeneratedModule } from '../generated/generated.module';
import { BROKER_CONFIGURATION } from '../generated/services-wrapper';
import { ToolbarPrincipalComponent } from './compartilhados/toolbar-principal/toolbar-principal.component';
import { MaterialModule } from './modules/material/material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { PessoasComponent } from './pages/pessoas/pessoas.component';
import { PessoaFormComponent } from './pages/pessoas/pessoa-form/pessoa-form.component';

import { PessoasDetalharComponent } from './pages/pessoas/pessoas-detalhar/pessoas-detalhar.component';
import { TreinosComponent } from './pages/treinos/treinos.component';
import { TreinosFormComponent } from './pages/treinos/treinos-form/treinos-form.component';
import { GruposMuscularesComponent } from './pages/grupos-musculares/grupos-musculares.component';
import { GrupoMuscularFormComponent } from './pages/grupos-musculares/grupo-muscular-form/grupo-muscular-form.component';
import { GrupoMuscularDetalharComponent } from './pages/grupos-musculares/grupo-muscular-detalhar/grupo-muscular-detalhar.component';
import { ExerciciosComponent } from './pages/exercicios/exercicios.component';
import { ExerciciosFormComponent } from './pages/exercicios/exercicios-form/exercicios-form.component';
import { ExerciciosDetalharComponent } from './pages/exercicios/exercicios-detalhar/exercicios-detalhar.component';
import { EquipamentosComponent } from './pages/equipamentos/equipamentos.component';
import { EquipamentosFormComponent } from './pages/equipamentos/equipamentos-form/equipamentos-form.component';
import { EquipamentosDetalharComponent } from './pages/equipamentos/equipamentos-detalhar/equipamentos-detalhar.component';
import { EquipamentoDialogComponent } from './pages/dialogs/equipamento-dialog/equipamento-dialog.component';
import { HttpClientModule } from '@angular/common/http';
import { GrupoMuscularDialogComponent } from './pages/dialogs/grupo-muscular-dialog/grupo-muscular-dialog.component';
import { MensagemAlertaService } from './services/mensagem-alerta.service';
import { TreinosDetalharComponent } from './pages/treinos/treinos-detalhar/treinos-detalhar.component';
import { PessoaDialogComponent } from './pages/dialogs/pessoa-dialog/pessoa-dialog.component';
import { MAT_DATE_LOCALE, MatPaginatorIntl } from '@angular/material';
import { TreinoExercicioFormComponent } from './pages/treinos/treino-exercicio-form/treino-exercicio-form.component';
import { getLabelsPaginatorIntl } from './compartilhados/labels-paginator';
import { PaginatorDefaultComponent } from './compartilhados/paginator-default/paginator-default.component';
import { RemoveRowTableService } from './services/remove-row-table.service';
import { UsuarioSessaoService } from './services/usuario-sessao-service';
// import { LabelsPadraoDatePicker } from './compartilhados/time-picker-label/timer-picker-label';

/* 
export class LabelsPadraoDatePicker extends OwlDateTimeIntl = {
  upSecondLabel   = 'Adicionar um segundo',
  downSecondLabel = 'Diminuir um segundo',
  upMinuteLabel   = 'Adicionar um minuto',
  downMinuteLabel = 'Diminuir um minuto',
  upHourLabel     = 'Acicionar uma hora',
  downHourLabel   = 'Diminuir uma hora',
  prevMonthLabel  = 'Voltar um mês',
  nextMonthLabel  = 'Próximo mês',
  prevYearLabel   = 'Ano anterior',
  nextYearLabel   = 'Próximo mês',
  prevMultiYearLabel= 'Voltar 21 anos',
  nextMultiYearLabel= 'Avançar 21 anos',
  switchToMonthViewLabel= 'Alterar para visualizar meses',
  switchToMultiYearViewLabel= 'Alterar para mes e ano',
  cancelBtnLabel= 'Cancelar',
  setBtnLabel= 'Selecionar',
  rangeFromLabel= 'De',
  rangeToLabel= 'Até',
  hour12AMLabel= 'AM',
  hour12PMLabel= 'PM'
}; */

@NgModule({
  declarations: [
    AppComponent,
    PessoaFormComponent,
    ToolbarPrincipalComponent,
    PessoasComponent,
    PessoasDetalharComponent,
    TreinosComponent,
    TreinosFormComponent,
    GruposMuscularesComponent,
    GrupoMuscularFormComponent,
    GrupoMuscularDetalharComponent,
    ExerciciosComponent,
    ExerciciosFormComponent,
    ExerciciosDetalharComponent,
    EquipamentosComponent,
    EquipamentosFormComponent,
    EquipamentosDetalharComponent,
    EquipamentoDialogComponent,
    GrupoMuscularDialogComponent,
    TreinosDetalharComponent,
    PessoaDialogComponent,
    TreinoExercicioFormComponent,
    PaginatorDefaultComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GeneratedModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule,
    NgxMaskModule.forRoot(),
    LoadingModule,
    HttpClientModule,
    OwlDateTimeModule, 
    OwlNativeDateTimeModule,
  ],
  providers: [{
    provide: BROKER_CONFIGURATION,
    useValue: {
      path: '/broker',
      realTime: false
    }
  },
  MensagemAlertaService,
  RemoveRowTableService,
  UsuarioSessaoService,
  {provide: MAT_DATE_LOCALE, useValue: 'pt-BR'},
  { provide: MatPaginatorIntl, useValue: getLabelsPaginatorIntl() }
  // {provide: OwlDateTimeIntl, useValue: LabelsPadraoDatePicker}
  ],
  entryComponents: [
    EquipamentoDialogComponent,
    GrupoMuscularDialogComponent,
    PessoaDialogComponent
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
