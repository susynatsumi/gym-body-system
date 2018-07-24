import { NgModule } from '@angular/core';

// material imports
import { MatButtonModule, MatToolbarModule, MatInputModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatIconModule, MatMenuModule, MatCheckboxModule, MatStepperModule, MatCardModule} from '@angular/material';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

// fim material imports

// MODULO PARA IMPORTACAO DOS MODULOS DO ANGULAR MATERIAL

@NgModule({
  imports: [
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatIconModule,
    MatMenuModule,
    MatCheckboxModule,
    MatStepperModule,
    MatAutocompleteModule,
    MatCardModule,
  ],
  exports: [
    MatButtonModule,
    MatToolbarModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatIconModule,
    MatMenuModule,
    MatCheckboxModule,
    MatStepperModule,
    MatAutocompleteModule,
    MatCardModule,
  ],
})
export class MaterialModule { }
