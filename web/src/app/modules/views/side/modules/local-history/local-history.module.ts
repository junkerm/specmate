import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { LocalHistory } from './components/local-history.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    LocalHistory
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    LocalHistory
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class LocalHistoryModule { }
