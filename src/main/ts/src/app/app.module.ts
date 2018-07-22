// angular imports 
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FlexLayoutModule  } from '@angular/flex-layout';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// fim angular imports

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

@NgModule({
  declarations: [
    AppComponent,
    PessoaFormComponent,
    ToolbarPrincipalComponent,
    PessoasComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GeneratedModule,
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [{
    provide: BROKER_CONFIGURATION,
    useValue: {
      path: '/broker',
      realTime: false
    }
  }],
  bootstrap: [AppComponent],
})
export class AppModule { }
