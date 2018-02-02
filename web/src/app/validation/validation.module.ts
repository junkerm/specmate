import { NgModule } from '@angular/core';
import { DuplicateNodeValidator } from './ceg/duplicate-node-validator';
import { InvalidValidator } from './ceg/invalid-validator';

@NgModule({
  imports: [
    // MODULE IMPORTS
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
    DuplicateNodeValidator,
    InvalidValidator
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ValidationModule { }
