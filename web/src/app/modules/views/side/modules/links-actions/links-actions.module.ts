import { NgModule } from '@angular/core';
import { LinksActions } from './components/links-actions.component';
import { BrowserModule } from '@angular/platform-browser';
import { NavigatorModule } from '../../../../navigation/modules/navigator/navigator.module';
import { TestSpecificationGeneratorButtonModule } from
  '../../../../actions/modules/test-specification-generator-button/test-specification-generator-button.module';
import { ExportTestprocedureButtonModule } from
  '../../../../actions/modules/export-testprocedure-button/export-testprocedure-button.module';
import { AdditionalInformationService } from './services/additional-information.service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthModule } from '../../../main/authentication/modules/auth/auth.module';
import { GetTestSpecificationSkeletonButtonModule } from
  '../../../../actions/modules/get-test-specification-skeleton-button/get-test-specification-skeleton-button.module';
import { SpecmateSharedModule } from '../../../../specmate/specmate.shared.module';
import { CegModelGeneratorButtonModule } from '../../../../actions/modules/ceg-model-generator-button/ceg-model-generator-button.module';
import { TranslateModule } from '@ngx-translate/core';
// tslint:disable-next-line:max-line-length
import { ExportTestspecificationButtonModule } from '../../../../actions/modules/export-testspecification-button/export-testspecification-button.module';

@NgModule({
  imports: [
    // MODULE IMPORTS
    BrowserModule,
    NavigatorModule,
    TestSpecificationGeneratorButtonModule,
    ExportTestprocedureButtonModule,
    GetTestSpecificationSkeletonButtonModule,
    ExportTestspecificationButtonModule,
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
