import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module'
import { CEGEditor } from './ceg-editor.component';
import { CEGGraphicalNode } from './ceg-graphical-node.component';

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
        CEGGraphicalNode
    ],
    providers: [D3Service],
})
export class CEGEditorModule { }
