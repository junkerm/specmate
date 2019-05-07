import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';
import { Welcome } from './components/welcome.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    CommonModule,
    SpecmateSharedModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    Welcome
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    Welcome
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class WelcomePageModule { }
