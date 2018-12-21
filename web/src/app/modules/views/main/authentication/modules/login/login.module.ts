import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Login } from './components/login.component';
import { FormsModule } from '@angular/forms';
import { AuthModule } from '../auth/auth.module';
import { I18NModule } from '../../../../../common/modules/i18n/i18n.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SpecmateSharedModule } from '../../../../../specmate/specmate.shared.module';

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
