import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ProjectExplorer } from '../core/project-explorer.component';
import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';
import { ModelEditor } from './model-editor/model-editor.component';
import { CEGNodeDetails } from './model-editor/ceg-node-details.component';

const requirementsRoutes: Routes = [
  {
    path: 'requirements',
    component: RequirementsPerspective,
    children: [{
      path: ':url/ceg',
      component: ModelEditor,
      outlet: 'main',
    }, {
      path: ':url/new-ceg',
      component: ModelEditor,
      outlet: 'main'
    }, {
      path: ':url',
      component: RequirementsDetails,
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
  imports: [RouterModule.forChild(requirementsRoutes)],
  exports: [RouterModule],
})
export class RequirementsRoutingModule { }
