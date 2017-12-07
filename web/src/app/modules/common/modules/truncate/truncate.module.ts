import { NgModule } from '@angular/core';
import { TruncatePipe } from './pipes/truncate-pipe';

@NgModule({
  imports: [
    // MODULE IMPORTS
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    TruncatePipe
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    TruncatePipe
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class TruncateModule { }