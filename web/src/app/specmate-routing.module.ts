import {TestSpecificationEditor} from './components/tests/test-specification-editor.component';
import {TestProcedureEditor} from './components/tests/test-procedure-editor.component';
import { RequirementsDetails } from './components/requirements/requirement-details.component';
import { ModelEditor } from './components/requirements/model-editor/model-editor.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFound } from './components/page-not-found.component';
import { CEGModel } from "./model/CEGModel";
import { Requirement } from "./model/Requirement";
import { TestProcedure } from "./model/TestProcedure";
import { TestSpecification } from "./model/TestSpecification";

const routes: Routes = [
  {
    //path: 'cause-effect-graph/:url',
    path: CEGModel.className + '/:url',
    component: ModelEditor,
  }, {
    path: Requirement.className + '/:url',
    component: RequirementsDetails
  }, {
    path: TestProcedure.className + '/:url',
    component: TestProcedureEditor
  }, {
    path: TestSpecification.className + '/:url',
    component: TestSpecificationEditor
  },
  { path: '**', component: PageNotFound }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class SpecmateRoutingModule { }


