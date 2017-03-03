import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';
import { RequirementsCEGEditor } from './requirements-ceg-editor.component';
import { CEGGraphicalNode } from './ceg-editor/ceg-graphical-node.component';

import { CoreModule } from '../core/core.module';
import { RequirementsRoutingModule } from './requirements-routing.module';

@NgModule({
    imports: [
        CoreModule,
        RequirementsRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ],
    declarations: [
        RequirementsPerspective,
        RequirementsDetails,
        RequirementsCEGEditor,
        CEGGraphicalNode
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
