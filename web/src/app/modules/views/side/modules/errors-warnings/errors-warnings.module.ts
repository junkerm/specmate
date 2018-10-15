import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { ErrorsWarings } from './components/errors-warnings.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    NgbModule.forRoot(),
    TranslateModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    ErrorsWarings
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
