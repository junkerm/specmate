import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ValidationModule } from '../../../../../forms/modules/validation/validation.module';
import { GraphicalEditorModule } from '../graphical-editor/graphical-editor.module';
import { ToolPalletteModule } from '../tool-pallette/tool-pallette.module';
import { ProcessDetails } from './components/process-details.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    GraphicalEditorModule,
    BrowserModule,
    ToolPalletteModule,
    ValidationModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ProcessDetails
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ProcessDetails
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ProcessEditorModule { }
