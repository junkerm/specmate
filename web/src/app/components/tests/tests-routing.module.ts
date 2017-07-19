import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProjectExplorer } from '../core/project-explorer.component';
import { TestsPerspective } from './tests-perspective.component';
import { TestSpecificationEditor } from './test-specification-editor.component';
import {TestProcedureEditor} from './test-procedure-editor.component';

const testRoutes: Routes = [
  {
    path: 'tests',
    component: TestsPerspective,
    children: [ {
      path: ':url/tpe',
      component: TestProcedureEditor,
      outlet: 'main',
    },{
      path: ':url',
      component: TestSpecificationEditor,
      outlet: 'main'
    },
    {
      path: '',
      component: ProjectExplorer,
      outlet: 'left'
    }]
  }
];

@NgModule({
  imports: [RouterModule.forChild(testRoutes)],
  exports: [RouterModule],
})
export class TestsRoutingModule { }
