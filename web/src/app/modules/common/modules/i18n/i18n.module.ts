import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { LanguageChooser } from './components/language-chooser.component';

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
