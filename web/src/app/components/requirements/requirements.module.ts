import { NgModule } from '@angular/core';

import { RequirementsDetails } from './requirement-details.component';

import { CoreModule } from '../core/core.module';
import { ModelEditorModule } from './model-editor/model-editor.module';

@NgModule({
    imports: [
        CoreModule,
        ModelEditorModule
    ],
    declarations: [
        RequirementsDetails
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
