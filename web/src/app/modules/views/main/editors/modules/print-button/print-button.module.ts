import { NgModule } from '@angular/core';
import { PrintButton } from './components/print-button.component';
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
    PrintButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    PrintButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class PrintButtonModule { }
