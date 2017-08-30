import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module';
import { SpecmateFormsModule } from '../../forms/specmate-forms.module';
import { ModelEditor } from './model-editor.component';
import { CEGEditor } from './ceg-editor.component';
import { CEGGraphicalNode } from './ceg-graphical-node.component';
import { CEGGraphicalConnection } from './ceg-graphical-connection.component';
import { CEGNodeDetails } from './ceg-node-details.component';
import { CEGGraphicalArc } from "./ceg-graphical-arc.component";

@NgModule({
    imports: [
        CoreModule,
        FormsModule,
        SpecmateFormsModule
    ],
    exports: [],
    declarations: [
        ModelEditor,
        CEGEditor,
        CEGGraphicalNode,
        CEGGraphicalArc,
        CEGGraphicalConnection,
        CEGNodeDetails
    ],
    providers: [],
})
export class ModelEditorModule { }
