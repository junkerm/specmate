import { NgModule } from '@angular/core';
import { ValidationService } from './services/validation.service';
import { NavigatorModule } from '../../../navigation/modules/navigator/navigator.module';
import { ValidateButton } from './components/validate-button.component';
import { TranslateModule } from '@ngx-translate/core';
import { BrowserModule } from '@angular/platform-browser';

@NgModule({
  imports: [
    // MODULE IMPORTS
    NavigatorModule,
    TranslateModule,
    BrowserModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ValidateButton
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ValidateButton
  ],
  providers: [
    // SERVICES
    ValidationService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ValidationModule { }
