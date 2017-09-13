import { NgModule } from '@angular/core';

import { CoreModule } from '../core/core.module';
import { SpecmateFormsModule } from '../forms/specmate-forms.module';
import { ProcessDetails } from "./process-details.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { DndModule } from "ng2-dnd";
import { ProcessEditorModule } from "./model-editor/process-editor.module";
import { ProcessEditor } from "./model-editor/process-editor.component";
import { BrowserModule } from "@angular/platform-browser";

@NgModule({
    imports: [
        BrowserModule,
        SpecmateFormsModule,
        ProcessEditorModule
    ],
    declarations: [
        ProcessDetails
    ],
    providers: [],
    exports: [],
})
export class ProcessesModule { }
