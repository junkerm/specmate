import { NgModule } from '@angular/core';
import { ExportTestspecificationButton } from './components/export-testspecification-button.component';
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
    ExportTestspecificationButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ExportTestspecificationButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ExportTestspecificationButtonModule { }
