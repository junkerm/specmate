import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TranslateModule } from '@ngx-translate/core';
import { CegModelGeneratorButtonModule } from '../../../../actions/modules/ceg-model-generator-button/ceg-model-generator-button.module';
import { ExportTestprocedureButtonModule } from '../../../../actions/modules/export-testprocedure-button/export-testprocedure-button.module';
import { GetTestSpecificationSkeletonButtonModule } from '../../../../actions/modules/get-test-specification-skeleton-button/get-test-specification-skeleton-button.module';
import { TestSpecificationGeneratorButtonModule } from '../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
import { AuthModule } from '../../../main/authentication/modules/auth/auth.module';
import { LinksActions } from './components/links-actions.component';
import { AdditionalInformationService } from './services/additional-information.service';
import { GetTestProcedureSkeletonButtonModule } from '../../../../actions/modules/get-test-procedure-skeleton-button/get-test-procedure-skeleton-button.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    TestSpecificationGeneratorButtonModule,
    ExportTestprocedureButtonModule,
    GetTestSpecificationSkeletonButtonModule,
    GetTestProcedureSkeletonButtonModule,
    SpecmateSharedModule,
    TranslateModule,
    CegModelGeneratorButtonModule,
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
