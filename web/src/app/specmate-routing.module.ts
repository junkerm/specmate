import { Url } from './util/Url';
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


const views = [ModelEditor, RequirementsDetails, TestProcedureEditor, TestSpecificationEditor];

const routes: Routes = [];
for(let i = 0; i < views.length; i++) {
  routes.push({
    path: Url.basePath(views[i].modelElementClass),
    component: views[i],
    canDeactivate: [UnsavedChangesGuard]
  });
}
/*
const routes: Routes = [
  {
    path: Url.basePath(ModelEditor.modelElementClass) + '/:url',
    component: ModelEditor,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Url.basePath(RequirementsDetails.modelElementClass) + '/:url',
    component: RequirementsDetails,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Url.basePath(TestProcedureEditor.modelElementClass) + '/:url',
    component: TestProcedureEditor,
    canDeactivate: [UnsavedChangesGuard]
  }, {
    path: Url.basePath(TestSpecificationEditor.modelElementClass) + '/:url',
    component: TestSpecificationEditor,
    canDeactivate: [UnsavedChangesGuard]
  },
  { path: '**', component: PageNotFound }
];
*/
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class SpecmateRoutingModule { }


