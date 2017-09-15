import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module';
import { SpecmateFormsModule } from '../../forms/specmate-forms.module';
import { CEGModelDetails } from './ceg-model-details.component';
import { GraphicalEditor } from './graphical-editor.component';
import { CEGGraphicalNode } from './ceg-graphical-node.component';
import { CEGGraphicalConnection } from './ceg-graphical-connection.component';
import { GraphicalElementDetails } from './graphical-element-details.component';
import { CEGGraphicalArc } from "./ceg-graphical-arc.component";

@NgModule({
    imports: [
        CoreModule,
        FormsModule,
        SpecmateFormsModule
    ],
    exports: [],
    declarations: [
        CEGModelDetails,
        GraphicalEditor,
        CEGGraphicalNode,
        CEGGraphicalArc,
        CEGGraphicalConnection,
        GraphicalElementDetails
    ],
    providers: [],
})
export class CEGModelEditorModule { }
