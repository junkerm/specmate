import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { TestExportButton } from './components/test-export-button.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateSharedModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
   TestExportButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TestExportButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TestExportButtonModule { }
