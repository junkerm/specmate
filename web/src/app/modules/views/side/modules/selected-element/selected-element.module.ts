import { NgModule } from '@angular/core';
import { AuthModule } from '../../../main/authentication/modules/auth/auth.module';
import { SelectedElementService } from './services/selected-element.service';

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
