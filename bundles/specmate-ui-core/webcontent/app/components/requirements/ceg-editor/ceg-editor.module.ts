import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module';
import { CEGEditor } from './ceg-editor.component';
import { CEGGraphicalNode } from './ceg-graphical-node.component';
import { CEGGraphicalConnection } from './ceg-graphical-connection.component';
import { CEGNodeDetails } from './ceg-node-details.component';

import { D3Service } from 'd3-ng2-service';

@NgModule({
    imports: [ 
        CoreModule,
        FormsModule,
        ReactiveFormsModule
    ],
    exports: [],
    declarations: [
        CEGEditor,
        CEGGraphicalNode,
        CEGGraphicalConnection,
        CEGNodeDetails
    ],
    providers: [D3Service],
})
export class CEGEditorModule { }
