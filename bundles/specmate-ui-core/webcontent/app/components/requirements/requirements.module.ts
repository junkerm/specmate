import { NgModule } from '@angular/core';

import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';
import { CEGGraphicalNode } from './ceg-editor/ceg-graphical-node.component';

import { CoreModule } from '../core/core.module';
import { CEGEditorModule } from './ceg-editor/ceg-editor.module';
import { RequirementsRoutingModule } from './requirements-routing.module';

@NgModule({
    imports: [
        CoreModule,
        RequirementsRoutingModule,
        CEGEditorModule
    ],
    declarations: [
        RequirementsPerspective,
        RequirementsDetails
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
