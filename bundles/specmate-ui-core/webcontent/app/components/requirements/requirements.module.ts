import { NgModule } from '@angular/core';

import { RequirementsDetails } from './requirement-details.component';

import { CoreModule } from '../core/core.module';
import { SpecmateFormsModule } from '../forms/specmate-forms.module';
import { ModelEditorModule } from './model-editor/model-editor.module';

@NgModule({
    imports: [
        CoreModule,
        ModelEditorModule,
        SpecmateFormsModule
    ],
    declarations: [
        RequirementsDetails
    ],
    providers: [],
    exports: [],
})
export class RequirementsModule { }
