import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { ExportTestprocedureButton } from './components/export-testprocedure-button.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateSharedModule
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
