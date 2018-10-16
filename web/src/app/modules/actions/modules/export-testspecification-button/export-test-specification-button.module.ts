import { NgModule } from '@angular/core';
import { ExportTestSpecificationButton } from './components/export-test-specification-button.component';
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
    ExportTestSpecificationButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ExportTestSpecificationButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ExportTestSpecificationButtonModule { }
