import { NgModule } from '@angular/core';
import { MaximizeButton } from './components/maximize-button.component';
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
    MaximizeButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    MaximizeButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class MaximizeButtonModule { }
