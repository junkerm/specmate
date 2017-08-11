import { TestSpecificationEditor } from './components/tests/test-specification-editor.component';
import { TestProcedureEditor } from './components/tests/test-procedure-editor.component';
import { RequirementsDetails } from './components/requirements/requirement-details.component';
import { ModelEditor } from './components/requirements/model-editor/model-editor.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFound } from './components/page-not-found.component';
import { CEGModel } from "./model/CEGModel";
import { Requirement } from "./model/Requirement";
import { TestProcedure } from "./model/TestProcedure";
import { TestSpecification } from "./model/TestSpecification";
import { UnsavedChangesGuard } from './guards/unsaved-changes-guard';
import { SpecmateViewBase } from './components/core/views/specmate-view-base';

const routes: Routes = [
  {
    path: CEGModel.className + '/:url',
    component: ModelEditor,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Requirement.className + '/:url',
    component: RequirementsDetails,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: TestProcedure.className + '/:url',
    component: TestProcedureEditor,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: TestSpecification.className + '/:url',
    component: TestSpecificationEditor,
    canDeactivate: [UnsavedChangesGuard]
  },
  { path: '**', component: PageNotFound }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class SpecmateRoutingModule { }


