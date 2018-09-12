import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { I18NModule } from '../../../../../common/modules/i18n/i18n.module';
import { AuthModule } from '../auth/auth.module';
import { Login } from './components/login.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    TranslateModule,
    I18NModule,
    CommonModule,
    FormsModule,
    AuthModule,
    NgbModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    Login
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class LoginModule { }
