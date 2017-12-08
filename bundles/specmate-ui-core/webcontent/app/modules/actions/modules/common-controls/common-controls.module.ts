import { NgModule } from '@angular/core';
import { CommonControls } from './components/common-controls.component';
import { BrowserModule } from '@angular/platform-browser';
import { EditorCommonControlService } from './services/common-control.service';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule
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
    EditorCommonControlService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class CommonControlsModule { }
