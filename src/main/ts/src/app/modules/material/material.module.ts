import { NgModule } from '@angular/core';

// material imports
import { MatButtonModule, MatToolbarModule, MatInputModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatIconModule, MatMenuModule, MatCheckboxModule, MatStepperModule } from '@angular/material';
import {MatAutocompleteModule} from '@angular/material/autocomplete';

// fim material imports

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
    MatAutocompleteModule
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
    MatAutocompleteModule
  ],
})
export class MaterialModule { }
