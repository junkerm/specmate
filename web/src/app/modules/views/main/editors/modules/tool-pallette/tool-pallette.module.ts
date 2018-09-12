import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { ToolPallette } from './components/tool-pallette.component';
import { EditorToolsService } from './services/editor-tools.service';
import { MultiselectionService } from './services/multiselection.service';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ToolPallette
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ToolPallette
  ],
  providers: [
    // SERVICES
    EditorToolsService,
    MultiselectionService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ToolPalletteModule { }
