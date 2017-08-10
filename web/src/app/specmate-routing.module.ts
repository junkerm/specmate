import {TestSpecificationEditor} from './components/tests/test-specification-editor.component';
import {TestProcedureEditor} from './components/tests/test-procedure-editor.component';
import { RequirementsDetails } from './components/requirements/requirement-details.component';
import { ModelEditor } from './components/requirements/model-editor/model-editor.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFound } from './components/page-not-found.component';

const routes: Routes = [
  {
    path: 'cause-effect-graph/:url',
    component: ModelEditor,
  }, {
    path: 'new-cause-effect-graph/:url',
    component: ModelEditor
  }, {
    path: 'requirement/:url',
    component: RequirementsDetails
  }, {
    path: 'test-procedure/:url',
    component: TestProcedureEditor
  }, {
    path: 'test-specification/:url',
    component: TestSpecificationEditor
  },
  { path: '**', component: PageNotFound }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: true })],
  exports: [RouterModule]
})

export class SpecmateRoutingModule { }


