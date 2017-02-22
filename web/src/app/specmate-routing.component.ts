import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RequirementsPerspective }  from './components/perspectives/requirements-perspective.component';

const routes: Routes = [
  { path: '', redirectTo: '/requirements', pathMatch: 'full' },
  { path: 'requirements',  component: RequirementsPerspective }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class SpecmateRoutingModule {}
