import { NgModule } from '@angular/core';
import { ExportTestprocedureButton } from './components/export-testprocedure-button.component';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ExportTestprocedureButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ExportTestprocedureButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ExportTestprocedureButtonModule { }
