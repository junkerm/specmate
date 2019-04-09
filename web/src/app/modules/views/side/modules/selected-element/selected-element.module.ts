import { NgModule } from '@angular/core';
import { SelectedElementService } from './services/selected-element.service';
import { AuthModule } from '../../../main/authentication/modules/auth/auth.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    AuthModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
    SelectedElementService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class SelectedElementModule { }
