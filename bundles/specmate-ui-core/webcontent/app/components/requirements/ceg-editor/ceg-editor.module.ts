import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module'
import { CEGEditor } from './ceg-editor.component';
import { CEGGraphicalNode } from './ceg-graphical-node.component';

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
    providers: [],
})
export class CEGEditorModule { }
