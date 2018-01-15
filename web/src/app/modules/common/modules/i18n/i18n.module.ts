import { NgModule } from '@angular/core';
import { LanguageChooser } from './components/language-chooser.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    LanguageChooser
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    LanguageChooser
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class I18NModule { }
