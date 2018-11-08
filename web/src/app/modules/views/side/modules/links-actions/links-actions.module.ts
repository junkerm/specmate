import { NgModule } from '@angular/core';
import { LinksActions } from './components/links-actions.component';
import { BrowserModule } from '@angular/platform-browser';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { TestSpecificationGeneratorButtonModule } from
  '../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module';
import { ExportTestprocedureButtonModule } from
  '../../../../actions/modules/export-testprocedure-button/export-testprocedure-button.module';
import { AdditionalInformationService } from './services/additional-information.service';
import { TranslateModule } from '@ngx-translate/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthModule } from '../../../main/authentication/modules/auth/auth.module';
import { GetTestSpecificationSkeletonButtonModule } from
  '../../../../actions/modules/get-test-specification-skeleton-button/get-test-specification-skeleton-button.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    TestSpecificationGeneratorButtonModule,
    ExportTestprocedureButtonModule,
    GetTestSpecificationSkeletonButtonModule,
    TranslateModule,
    AuthModule,
    NgbModule.forRoot()
  ],
  declarations: [
    // COMPONENTS IN THIS MODULE
    LinksActions
  ],
  exports: [
    // THE COMPONENTS VISIBLE TO THE OUTSIDE
    LinksActions
  ],
  providers: [
    // SERVICES
    AdditionalInformationService
  ],
  bootstrap: [
    // COMPONENTS THAT ARE BOOTSTRAPPED HERE
  ]
})

export class LinksActionsModule { }
