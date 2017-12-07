import { NgModule } from '@angular/core';
import { Welcome } from './components/welcome.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
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
