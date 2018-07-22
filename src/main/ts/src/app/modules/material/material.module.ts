import { NgModule } from '@angular/core';

// material imports
import { MatButtonModule, MatToolbarModule, MatInputModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatIconModule, MatMenuModule, MatCheckbox, MatCheckboxModule } from '@angular/material';
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
    MatCheckboxModule
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
    MatCheckboxModule
  ],
})
export class MaterialModule { }
