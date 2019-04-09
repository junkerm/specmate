import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TranslateModule } from '@ngx-translate/core';
import { ShortModelErrorMessageDisplay } from './components/short-model-error-message-display.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ShortModelErrorMessageDisplay
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ShortModelErrorMessageDisplay
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ShortModelErrorDisplayModule { }
