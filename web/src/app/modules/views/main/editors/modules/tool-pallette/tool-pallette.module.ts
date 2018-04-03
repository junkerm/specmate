import { NgModule } from '@angular/core';
import { ToolPallette } from './components/tool-pallette.component';
import { BrowserModule } from '@angular/platform-browser';
import { EditorToolsService } from './services/editor-tools.service';
import { TranslateModule } from '@ngx-translate/core';

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
    EditorToolsService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ToolPalletteModule { }
