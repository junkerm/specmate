import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ConnectionModule } from '../../../common/modules/connection/connection.module';
import { SpecmateSharedModule } from '../../../specmate/specmate.shared.module';
import { CommonControls } from './components/common-controls.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    SpecmateSharedModule,
    ConnectionModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    CommonControls
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    CommonControls
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class CommonControlsModule { }
