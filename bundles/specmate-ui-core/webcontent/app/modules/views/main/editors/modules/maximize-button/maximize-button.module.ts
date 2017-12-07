import { NgModule } from '@angular/core';
import { MaximizeButton } from './components/maximize-button.component';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule
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
