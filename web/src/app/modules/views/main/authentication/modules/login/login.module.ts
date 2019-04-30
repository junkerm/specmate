import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { I18NModule } from '../../../../../common/modules/i18n/i18n.module';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';
import { AuthModule } from '../auth/auth.module';
import { Login } from './components/login.component';

@NgModule({
  imports: [
    // MODULE IMPORTS
    I18NModule,
    CommonModule,
    FormsModule,
    AuthModule,
    SpecmateSharedModule,
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
