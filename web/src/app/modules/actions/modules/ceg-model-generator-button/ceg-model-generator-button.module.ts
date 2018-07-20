import { NgModule } from '@angular/core';
import { CegModelGeneratorButton } from './components/ceg-model-generator-button.component';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    CegModelGeneratorButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    CegModelGeneratorButton
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class CegModelGeneratorButtonModule { }
