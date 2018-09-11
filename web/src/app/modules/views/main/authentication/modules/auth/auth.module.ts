import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslateModule } from '@ngx-translate/core';
import { CookieModule } from 'ngx-cookie';
import { UserPermissionsGuard } from './guards/user-permissions-guard';
import { AuthenticationService } from './services/authentication.service';



@NgModule({
  imports: [
    // MODULE IMPORTS
    CommonModule,
    TranslateModule,
    CookieModule
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
  ],
  providers: [
    // SERVICES
    AuthenticationService,
    UserPermissionsGuard
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class AuthModule { }
