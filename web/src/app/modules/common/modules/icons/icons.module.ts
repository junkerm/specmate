import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IconSelector } from './components/icon-selector.component';

@NgModule({
  imports: [
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    IconSelector
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    IconSelector
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class IconsModule { }
