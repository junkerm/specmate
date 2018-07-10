import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { BrowserModule } from '@angular/platform-browser';
import { LanguageChooser } from './components/language-chooser.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    TranslateModule,
    NgbModule.forRoot()
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
