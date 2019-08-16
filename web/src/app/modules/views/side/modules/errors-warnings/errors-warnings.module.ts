import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
// tslint:disable-next-line:max-line-length
import { ShortModelErrorDisplayModule } from '../../../../actions/modules/short-error-message-display/short-model-error-message-display.module';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
import { ErrorsWarings } from './components/errors-warnings.component';
import { Warning } from './components/warning.component';
import { ValidationModule } from '../../../../forms/modules/validation/validation.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    SpecmateSharedModule,
    NgbModule.forRoot(),
    TranslateModule,
    ValidationModule,
    ShortModelErrorDisplayModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ErrorsWarings,
    Warning
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    ErrorsWarings
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class ErrorsWarningsModule { }
