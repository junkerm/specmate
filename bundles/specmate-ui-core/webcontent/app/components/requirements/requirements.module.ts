import { NgModule } from '@angular/core';

import { RequirementsPerspective } from './requirements-perspective.component';
import { RequirementsDetails } from './requirement-details.component';
import { CEGGraphicalNode } from './ceg-editor/ceg-graphical-node.component';

import { CoreModule } from '../core/core.module';
import { CEGEditorModule } from './ceg-editor/ceg-editor.module';
import { RequirementsRoutingModule } from './requirements-routing.module';

import { ModalModule, Overlay } from 'angular2-modal';
import { JSNativeModalModule  } from 'angular2-modal/plugins/js-native';

@NgModule({
    imports: [
        CoreModule,
        RequirementsRoutingModule,
        CEGEditorModule,
        ModalModule.forRoot(),
        JSNativeModalModule
    ],
    declarations: [
        RequirementsPerspective,
        RequirementsDetails
    ],
    providers: [],
    exports: [ModalModule],
})
export class RequirementsModule { }
