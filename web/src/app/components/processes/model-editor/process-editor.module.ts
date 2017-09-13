import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { CoreModule } from '../../core/core.module';
import { SpecmateFormsModule } from '../../forms/specmate-forms.module';
import { ProcessEditor } from "./process-editor.component";
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
    imports: [
        CoreModule,
        FormsModule,
        BrowserModule,
        SpecmateFormsModule
    ],
    exports: [
        ProcessEditor
    ],
    declarations: [
        ProcessEditor
    ],
    providers: []
})
export class ProcessEditorModule { }
