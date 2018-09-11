import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Config } from '../../../config/config';
import { CEGModel } from '../../../model/CEGModel';
import { Process } from '../../../model/Process';
import { Requirement } from '../../../model/Requirement';
import { TestProcedure } from '../../../model/TestProcedure';
import { TestSpecification } from '../../../model/TestSpecification';
import { Url } from '../../../util/url';
import { AuthModule } from '../../views/main/authentication/modules/auth/auth.module';
import { UserPermissionsGuard } from '../../views/main/authentication/modules/auth/guards/user-permissions-guard';
import { Login } from '../../views/main/authentication/modules/login/components/login.component';
import { LoginModule } from '../../views/main/authentication/modules/login/login.module';
import { CEGModelEditorModule } from '../../views/main/editors/modules/ceg-model-editor/ceg-model-editor.module';
import { CEGModelDetails } from '../../views/main/editors/modules/ceg-model-editor/components/ceg-model-details.component';
import { ProcessDetails } from '../../views/main/editors/modules/process-model-editor/components/process-details.component';
import { ProcessEditorModule } from '../../views/main/editors/modules/process-model-editor/process-editor.module';
import { RequirementsDetails } from '../../views/main/editors/modules/requirements-details/components/requirement-details.component';
import { RequirementsDetailsModule } from '../../views/main/editors/modules/requirements-details/requirements-details.module';
import { TestProcedureEditor } from '../../views/main/editors/modules/test-procedure-editor/components/test-procedure-editor.component';
import { TestProcedureEditorModule } from '../../views/main/editors/modules/test-procedure-editor/test-procedure-editor.module';
import { TestSpecificationEditor } from
  '../../views/main/editors/modules/test-specification-editor/components/test-specification-editor.component';
import { TestSpecificationEditorModule } from '../../views/main/editors/modules/test-specification-editor/test-specification-editor.module';
import { PageNotFound } from '../../views/main/static/modules/page-not-found/components/page-not-found.component';
import { PageNotFoundModule } from '../../views/main/static/modules/page-not-found/page-not-found.module';
import { Welcome } from '../../views/main/static/modules/welcome-page/components/welcome.component';
import { WelcomePageModule } from '../../views/main/static/modules/welcome-page/welcome-page.module';
import { UnsavedChangesGuard } from '../guards/unsaved-changes-guard';

const routes: Routes = [
  {
    path: Url.basePath(CEGModel) + '/:url',
    component: CEGModelDetails,
    canDeactivate: [UnsavedChangesGuard],
    canActivate: [UserPermissionsGuard]
  }, {
    path: Url.basePath(Requirement) + '/:url',
    component: RequirementsDetails,
    canDeactivate: [UnsavedChangesGuard],
    canActivate: [UserPermissionsGuard]
  }, {
    path: Url.basePath(TestProcedure) + '/:url',
    component: TestProcedureEditor,
    canDeactivate: [UnsavedChangesGuard],
    canActivate: [UserPermissionsGuard]
  }, {
    path: Url.basePath(TestSpecification) + '/:url',
    component: TestSpecificationEditor,
    canDeactivate: [UnsavedChangesGuard],
    canActivate: [UserPermissionsGuard]
  }, {
    path: Url.basePath(Process) + '/:url',
    component: ProcessDetails,
    canDeactivate: [UnsavedChangesGuard],
    canActivate: [UserPermissionsGuard]
  },
  { path: Config.LOGIN_URL, component: Login },
  { path: Config.WELCOME_URL, component: Welcome, canActivate: [UserPermissionsGuard] },
  { path: '', component: Welcome, canActivate: [UserPermissionsGuard] },
  { path: '**', component: PageNotFound, canActivate: [UserPermissionsGuard] }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    CEGModelEditorModule,
    ProcessEditorModule,
    RequirementsDetailsModule,
    TestProcedureEditorModule,
    TestSpecificationEditorModule,
    WelcomePageModule,
    PageNotFoundModule,
    LoginModule,
    AuthModule
  ],
  declarations: [],
  providers: [
    UnsavedChangesGuard
  ],
  exports: [RouterModule]
})
export class SpecmateRoutingModule { }
