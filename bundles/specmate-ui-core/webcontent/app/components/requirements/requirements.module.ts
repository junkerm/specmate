import { NgModule } from '@angular/core';

import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';

import { CoreModule } from '../core/core.module';
import { RequirementsRoutingModule } from './requirements-routing.module';

@NgModule({
    imports: [
        CoreModule,
        RequirementsRoutingModule
    ],
    declarations: [
        RequirementsPerspective,
        RequirementsDetails
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
