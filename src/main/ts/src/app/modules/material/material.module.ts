import { NgModule } from '@angular/core';

// material imports
import { MatButtonModule, MatToolbarModule, MatInputModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatIconModule, MatMenuModule, MatCheckboxModule, MatStepperModule, MatCardModule, MatDialogModule, MatGridListModule} from '@angular/material';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSnackBarModule} from '@angular/material/snack-bar';
// import {MatDividerModule} from '@angular/material/divider';

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
    MatDialogModule,
    MatGridListModule,
    MatSnackBarModule,
    // MatDividerModule,
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
    MatDialogModule,
    MatGridListModule,
    MatSnackBarModule,
    // MatDividerModule,
  ],
})
export class MaterialModule { }
