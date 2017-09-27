import { NgModule } from '@angular/core';

import { RequirementsDetails } from './requirement-details.component';

import { CoreModule } from '../core/core.module';
import { SpecmateFormsModule } from '../forms/specmate-forms.module';
import { CEGModelEditorModule } from './ceg-model-editor/ceg-model-editor.module';

@NgModule({
    imports: [
        CoreModule,
        CEGModelEditorModule,
        SpecmateFormsModule
    ],
    declarations: [
        RequirementsDetails
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
