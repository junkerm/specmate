import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module';
import { ModelEditor } from './model-editor.component';
import { CEGEditor } from './ceg-editor.component';
import { CEGGraphicalNode } from './ceg-graphical-node.component';
import { CEGGraphicalConnection } from './ceg-graphical-connection.component';
import { CEGNodeDetails } from './ceg-node-details.component';

import { D3Service } from 'd3-ng2-service';

@NgModule({
    imports: [
        CoreModule,
        FormsModule
    ],
    exports: [],
    declarations: [
        ModelEditor,
        CEGEditor,
        CEGGraphicalNode,
        CEGGraphicalConnection,
        CEGNodeDetails
    ],
    providers: [D3Service],
})
export class ModelEditorModule { }
