import { NgModule } from '@angular/core';

import { CoreModule } from '../core/core.module';
import { SpecmateFormsModule } from '../forms/specmate-forms.module';
import { ProcessDetails } from "./process-details.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { BrowserModule } from "@angular/platform-browser";
import { GraphicalModule } from "../core/graphical/graphical.module";

@NgModule({
    imports: [
        BrowserModule,
        CoreModule,
        SpecmateFormsModule,
        GraphicalModule
    ],
    declarations: [
        ProcessDetails
    ],
    providers: [],
    exports: [],
})
export class ProcessesModule { }
