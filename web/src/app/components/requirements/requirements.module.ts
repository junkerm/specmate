import { NgModule } from '@angular/core';

import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';

import { CoreModule } from '../core/core.module';
import { ModelEditorModule } from './model-editor/model-editor.module';
import { RequirementsRoutingModule } from './requirements-routing.module';

@NgModule({
    imports: [
        CoreModule,
        RequirementsRoutingModule,
        ModelEditorModule
    ],
    declarations: [
        RequirementsPerspective,
        RequirementsDetails
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
