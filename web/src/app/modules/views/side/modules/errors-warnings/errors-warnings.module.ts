import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { ErrorsWarings } from './components/errors-warnings.component';
import { Warning } from './components/warning.component';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
// tslint:disable-next-line:max-line-length
import { ShortModelErrorDisplayModule } from '../../../../actions/modules/short-error-message-display/short-model-error-message-display.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    SpecmateSharedModule,
    NgbModule.forRoot(),
    TranslateModule,
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
