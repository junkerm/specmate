import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';
import { RequirementsCEGEditor } from './requirements-ceg-editor.component';

import { CoreModule } from '../core/core.module';
import { RequirementsRoutingModule } from './requirements-routing.module';

@NgModule({
    imports: [
        CoreModule,
        RequirementsRoutingModule,
        FormsModule
    ],
    declarations: [
        RequirementsPerspective,
        RequirementsDetails,
        RequirementsCEGEditor
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
